package gk.jfilter.impl.filter.parser;

import gk.jfilter.JFilterException;
import gk.jfilter.impl.filter.bean.ArrayBean;
import gk.jfilter.impl.filter.bean.Bean;
import gk.jfilter.impl.filter.bean.CollectionBean;
import gk.jfilter.impl.filter.bean.MapBean;
import gk.jfilter.impl.filter.expression.AndFilterExpression;
import gk.jfilter.impl.filter.expression.ArrayFilterExpression;
import gk.jfilter.impl.filter.expression.ClassFilterExpression;
import gk.jfilter.impl.filter.expression.CollectionFilterExpression;
import gk.jfilter.impl.filter.expression.EmptyFilterExpression;
import gk.jfilter.impl.filter.expression.FilterExpression;
import gk.jfilter.impl.filter.expression.MapFilterExpression;
import gk.jfilter.impl.filter.expression.NotFilterExpression;
import gk.jfilter.impl.filter.expression.OrFilterExpression;
import gk.jfilter.impl.filter.expression.SimpleFilterExpression;
import gk.jfilter.impl.filter.json.JacksonJsonImpl;
import gk.jfilter.impl.filter.json.Json;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

public class FilterParser {
	private static Json json = new JacksonJsonImpl();
	private Bean bean;

	public FilterParser(Bean bean) {
		this.bean = bean;
	}

	public FilterExpression parse(String jsonFilter) {
		return parse(jsonFilter, null);
	}
	
	public FilterExpression parse(String jsonFilter, Map<String, ?> parameters) {
		try {
			Map<String, ?> filterMap = json.toMap(jsonFilter.getBytes());
			String filterFirstKey = (String) filterMap.keySet().toArray()[0];

			FilterExpression exp = new EmptyFilterExpression(filterFirstKey, bean);
			parseMap(filterMap, parameters, exp, bean);
			return exp;
		} catch (Throwable e) {
			throw new JFilterException("Filter parsing error.", e);
		}
	}

	private void parseMap(Map<String, ?> filterMap, Map<String, ?> parameters, FilterExpression exp, Bean bean) {
		try {
			for (Map.Entry<String, ?> filterMapEntry : filterMap.entrySet()) {
				parseKey(filterMapEntry, parameters, exp, bean);
			}
		} catch (Throwable e) {
			throw new JFilterException("Filter parsing error.", e);
		}
	}

	/**
	 * Replaces leaf key values FilterKeyValue object.
	 * 
	 * @param filterMapEntry
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private void parseKey(final Map.Entry<String, ?> filterMapEntry, Map<String, ?> parameters, FilterExpression exp, Bean bean) {
		String[] filterKeys = filterMapEntry.getKey().split("\\.");
		String filterKey = null;
		if(filterKeys.length>1) {
			//last
			filterKey = filterKeys[filterKeys.length-1];
			//except last
			exp = parseDots(Arrays.copyOfRange(filterKeys, 0, filterKeys.length-1), exp, bean);
			bean=exp.getBean();
		} else {
			filterKey = filterMapEntry.getKey();
		}
		
		Object filterValue = filterMapEntry.getValue();

		/** e.g. {a:"v"} */
		if (filterValue instanceof String) {
			parseProperty(filterKey, toArrays(filterValue, parameters), Operator.$eq, exp, bean);
			return;
		}

		/** e.g {a:{$gt:"10"}} , {a:{$in:[1,2,3]}} */
		if ((filterValue instanceof Map) && (containsOperator((Map<String, ?>) filterValue))) {
			Map<String, ?> valueMap = (Map<String, ?>) filterValue;
			String s = (String) valueMap.keySet().toArray()[0];
			Operator operator = Operator.operatorOf(s);
			
			parseProperty(filterKey, toArrays(valueMap.get(s), parameters), operator, exp, bean);
			return;
		}

		/** e.g. {$and:[{a:"v1"}, {b:"v2}]} */
		if (filterValue instanceof Collection) {
			if (!Operator.isJoin(filterKey)) {
				throw new JFilterException(" $and or $or or $not expected. with collection of expressions: " + filterValue+" but was "+filterKey);
			}
			parseCollection(filterKey, (Collection<Map<String, ?>>) filterValue, parameters, exp, bean);
			return;
		}

		/** e.g. {a:{b:"v1", c:"v2"}} */
		if (filterValue instanceof Map) {
			parseClass(filterKey, (Map<String, ?>) filterValue, parameters, exp, bean);
			return;
		}

		throw new JFilterException("Unknow filter parsing error.");
	}

	private void parseProperty(String filterKey, Object[] filterValues, Operator operator, FilterExpression exp, Bean bean) {
		Bean filterKeyBean = bean.getProperty(filterKey);
		FilterExpression nextExp;
		/** for collection and array of simple parameterized type */
		if (filterKeyBean instanceof CollectionBean) {
			nextExp = new CollectionFilterExpression(filterKey, filterKeyBean);
			nextExp.addExpression(new SimpleFilterExpression("get", filterValues, operator, filterKeyBean.getProperty("get")));
		} else if (filterKeyBean instanceof ArrayBean) {
			nextExp = new ArrayFilterExpression(filterKey, filterKeyBean);
			nextExp.addExpression(new SimpleFilterExpression("get", filterValues, operator, filterKeyBean.getProperty("get")));
		} else {
			nextExp = new SimpleFilterExpression(filterKey, filterValues, operator, filterKeyBean);
		}
		exp.addExpression(nextExp);
	}
	
	private void parseCollection(String filterKey, Collection<Map<String, ?>> filterValue, Map<String,?> parameters, FilterExpression exp, Bean bean) {
		FilterExpression nextExp;
		Operator operator = Operator.operatorOf(filterKey);
		if (operator == Operator.$and) {
			nextExp = new AndFilterExpression(filterKey, bean);
		} else if (operator == Operator.$or) {
			nextExp = new OrFilterExpression(filterKey, bean);
		} else if (operator == Operator.$not) {
			nextExp = new NotFilterExpression(filterKey, bean);
		} else {
			throw new JFilterException(" Join operator not supported: " + operator);
		}

		for (Map<String, ?> filterMap : filterValue) {
			parseMap(filterMap, parameters, nextExp, bean);
		}
		exp.addExpression(nextExp);
	}
	
	private void parseClass(String filterKey, Map<String,?> filterValue, Map<String,?> parameters, FilterExpression exp, Bean bean) {
		Bean filterKeyBean = bean.getProperty(filterKey);
		FilterExpression nextExp;
		
		if (filterKeyBean instanceof CollectionBean) {
			/** for properties which returns Collection objects */
			nextExp = new CollectionFilterExpression(filterKey, filterKeyBean);
		} else if (filterKeyBean instanceof ArrayBean) {
			/** for properties which returns Array objects */
			nextExp = new ArrayFilterExpression(filterKey, filterKeyBean);
		} else if (filterKeyBean instanceof MapBean) {
			/** for properties which returns Map objects */
			nextExp = new MapFilterExpression(filterKey, filterKeyBean);
		} else{
			nextExp = new ClassFilterExpression(filterKey, filterKeyBean);
		}

		parseMap(filterValue, parameters, nextExp, filterKeyBean);
		exp.addExpression(nextExp);
	}
	
	
	/**
	 * Returns last expression e.g. product.sku.price will return price expression.
	 * @param properties
	 * @param exp
	 * @param bean
	 * @return
	 */
	private FilterExpression parseDots(String[] properties, FilterExpression exp, Bean bean) {

		String nextProperty = properties[0];
		Bean nextBean = bean.getProperty(nextProperty);
		FilterExpression nextExp;
		
		if(nextBean.getType().isArray()) {
			nextExp = new ArrayFilterExpression(nextProperty, nextBean);
			exp.addExpression(nextExp);
		} else if (Collection.class.isAssignableFrom(nextBean.getType())) {
			nextExp = new CollectionFilterExpression(nextProperty, nextBean);
			exp.addExpression(nextExp);
		} else if (Map.class.isAssignableFrom(nextBean.getType())) {
			nextExp = new MapFilterExpression(nextProperty, nextBean);
			exp.addExpression(nextExp);
		}  else {
			nextExp = new ClassFilterExpression(nextProperty, nextBean);
			exp.addExpression(nextExp);
		}
		
		if(properties.length>1) {
			//recursion
			return parseDots(Arrays.copyOfRange(properties, 1, properties.length), nextExp, nextBean);
		}
		return nextExp;
	}

	private boolean containsOperator(final Map<String, ?> filterMap) {
		for (String key : filterMap.keySet()) {
			if (Operator.isComparator(key)) {
				return true;
			}
		}
		return false;
	}

	private Object[] toArrays(Object object, Map<String, ?> parameters) {
		if (object instanceof Collection) {
			return ((Collection<?>) object).toArray();
		} else {
			String s = (String) object;
			if (s.startsWith("?")) {
				
				if(!parameters.containsKey(s.substring(1))) {
					throw new JFilterException("Filter parameter [" + s + "] value not given");
				}
				
				Object o = parameters.get(s.substring(1));
				if (o instanceof Collection) {
					return ((Collection<?>) o).toArray();
				} else {
					return new Object[] { o };
				}
			} else {
				throw new JFilterException(
						"Filter parameter ["
								+ s
								+ "] should be in '?n' or '?s' where n is integer and s is string, and values should be supplied as Map or variable argument.");
			}
		}

	}

}
