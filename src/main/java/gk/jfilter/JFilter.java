package gk.jfilter;

import gk.jfilter.impl.filter.bean.Bean;
import gk.jfilter.impl.filter.bean.PrimitiveWrapper;
import gk.jfilter.impl.filter.bean.QueryBean;
import gk.jfilter.impl.filter.expression.FilterExpression;
import gk.jfilter.impl.filter.parser.FilterParser;
import gk.jfilter.impl.mr.m.MapParser;
import gk.jfilter.impl.mr.m.Mapper;
import gk.jfilter.impl.mr.r.Reducer;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Main class to filter/query collections (java.util.Collection, java.util.Map
 * or arrays). New instance of this class to be created for each collection.
 * Then this instance can be used to execute queries multiple times on the
 * collection. Following are different ways to execute filter.
 * <p>
 * <blockquote>
 * 
 * <pre>
 * Collection&lt;Pet&gt; pets = new ArrayList&lt;Pet&gt;();
 * JFilter jfilter = new JFilter(pets, Pet.class);
 * Collection&lt;Pet&gt; cats = filter.execute(&quot;{'type':'?1'}&quot;, &quot;CAT&quot;);
 * 
 * Collection&lt;Pet&gt; pets = new ArrayList&lt;Pet&gt;();
 * JFilter jfilter = new JFilter(pets, Pet.class);
 * Map&lt;String, ?&gt; parameters = new HashMap&lt;String, String&gt;();
 * 
 * parameters.put(&quot;type&quot;, &quot;CAT&quot;);
 * Collection&lt;Pet&gt; cats = jfilter.execute(&quot;{'type':'?type'}&quot;, parameters);
 * 
 * parameters.put(&quot;type&quot;, &quot;DOG&quot;);
 * Collection&lt;Pet&gt; dogs = jfilter.execute(&quot;{'type':'?type'}&quot;, parameters);
 * 
 * </pre>
 * 
 * <blockquote>
 * </p>
 * 
 * @author Kamran Ali Khan (khankamranali@gmail.com)
 * 
 */
public class JFilter<T> {
	private Bean bean;
	private FilterParser filterParser;
	/** Supported collection types */
	private Iterable<T> iterable;

	/**
	 * 
	 * @param collection
	 *            collection to be filtered.
	 * @param klass
	 *            collection parameterized class object.
	 */
	public JFilter(Iterable<T> iterable, Class<T> klass) {
		init(klass);
		this.iterable = iterable;
	}

	/**
	 * For making clone.
	 * 
	 * @param iterable
	 * @param bean
	 */
	private JFilter(Iterable<T> iterable, Bean bean, FilterParser filterParser) {
		this.iterable = iterable;
		this.bean = bean;
		this.filterParser = filterParser;
	}

	private void init(Class<T> klass) {
		try {
			this.bean = new QueryBean(klass);
			this.filterParser = new FilterParser(bean);
		} catch (IntrospectionException e) {
			throw new JFilterException("Unable to introspect the bean class.");
		}
	}

	private List<T> execute(Iterator<T> itr, FilterExpression filterExp) {
		List<T> result = new ArrayList<T>();

		while (itr.hasNext()) {
			T o = itr.next();
			if (filterExp.eval(o)) {
				result.add(o);
			}
		}
		return result;
	}

	/**
	 * Executes filter with map of parameter values. filter parameter values are
	 * given in key value form where key is string given in filter in "?string"
	 * format and value is a object of same class as of bean property. $in and
	 * $nin values are given as List of objects.
	 * 
	 * @param filter
	 *            Json filter string e.g. "{'id':'?id'}"
	 * @param parameters
	 *            Filter parameters values are given in key value form where key
	 *            is string given in filter in "?string" format and value is
	 *            object of bean property class. $in and $nin values are given
	 *            as List of objects.
	 * 
	 * @return filtered collection.
	 */
	public JFilter<T> filter(String filter, Map<String, ?> parameters) {
		FilterExpression filterExp = filterParser.parse(filter, parameters);
		return new JFilter<T>(execute(iterable.iterator(), filterExp), bean, filterParser);
	}

	/**
	 * Executes filter with array of parameter values. Parameters are given as
	 * "?1", "?2" etc in the filter, starting from "?1" to "?n" where n is
	 * integer. Parameter values are are picked from corresponding argument
	 * position in the variable arguments.
	 * 
	 * @param filter
	 *            Json filter string e.g. "{'id':'?1'}"
	 * @param parameters
	 *            Filter parameters value objects are given in the same order as
	 *            given in filter in the form of "?1", "?2" ..."?n". The object
	 *            should be of bean property class. $in and $nin values are
	 *            given as List of objects.
	 * 
	 * @return filtered values.
	 */
	public JFilter<T> filter(String filter, Object... parameters) {
		return filter(filter, getParameterMap(parameters));
	}

	/**
	 * Returns first element of the filtered values.
	 * 
	 * @return First element of the filtered values.
	 */
	public T getFirst() {
		return iterable.iterator().next();
	}

	/**
	 * Selects given property from the collection and creates new JFilter
	 * object.
	 * 
	 * @param property
	 *            property/method name of the bean, dot notation should be used
	 *            for inner properties e.g. "sku.price".
	 * @return JFilter
	 */
	@SuppressWarnings("unchecked")
	public <U> JFilter<U> map(String property) {
		List<U> list = new ArrayList<U>();
		Mapper mapper = MapParser.parse(bean, property);
		for (T o : iterable) {
			mapper.map(o, list);
		}
		return new JFilter<U>(list, (Class<U>) mapper.getType());
	}

	/**
	 * Returns maximum object from the collection, bean class should implement
	 * Comparable interface.
	 * 
	 * @return Maximum object from the collection.
	 * @throws JFilterException
	 *             if bean class does not implement Comparable interface.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public T max() {
		if (!Comparable.class.isAssignableFrom(PrimitiveWrapper.wrap(bean.getType()))) {
			throw new JFilterException("Reduce function on type: " + bean.getType() + " is not supported.");
		}

		return (T) Reducer.max((Iterable<Comparable>) iterable);
	}

	/**
	 * Returns minimum object from the collection, bean class should implement
	 * Comparable interface.
	 * 
	 * @return Minimum object from the collection.
	 * @throws JFilterException
	 *             if bean class does not implement Comparable interface.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public T min() {
		if (!Comparable.class.isAssignableFrom(PrimitiveWrapper.wrap(bean.getType()))) {
			throw new JFilterException("Reduce function on type: " + bean.getType() + " is not supported.");
		}

		return (T) Reducer.min((Iterable<Comparable>) iterable);
	}

	/**
	 * Returns sum of numbers in the collection, bean class should extends Number
	 * abstract class, e.g. Integer, Float, Double, BigInteger, BigDecimal etc.
	 * 
	 * @return Minimum object from the collection.
	 * @throws JFilterException
	 *             if bean class does not extends Number class.
	 */
	@SuppressWarnings("unchecked")
	public T sum() {
		if (!Number.class.isAssignableFrom(PrimitiveWrapper.wrap(bean.getType()))) {
			throw new JFilterException("Reduce function on type: " + bean.getType() + " is not supported.");
		}
		return (T) Reducer.sum((Iterable<Number>) iterable);
	}

	/**
	 * Returns number of element in the collection.
	 * abstract class, e.g. Integer, Float, Double, BigInteger, BigDecimal etc.
	 * 
	 * @return Minimum object from the collection.
	 * @throws JFilterException
	 *             if bean class does not implement Comparable interface.
	 */

	public int count() {
		return Reducer.count(iterable);
	}

	/**
	 * e.g. {'id'}, {'id':'desc'}, {'id', 'skus.price'}
	 * 
	 * @param property
	 * @return
	 */
	public JFilter<T> sortBy(String property) {
		throw new UnsupportedOperationException("Not yet implemented.");
	}

	/**
	 * Populates a given collection with the filter, map result. You need to call this method after filter or map method to collect the result.
	 * 
	 * @return Minimum object from the collection.
	 * @throws JFilterException
	 *             if bean class does not implement Comparable interface.
	 */

	public <U extends Collection<T>> U out(U collection) {
		for (T o : iterable) {
			collection.add(o);
		}
		return collection;
	}

	public void out(Map<?, ?> c) {
		throw new UnsupportedOperationException("Not yet implemented.");
	}

	private Map<String, Object> getParameterMap(Object... parameters) {
		Map<String, Object> map = new HashMap<String, Object>();

		Integer i = 1;
		for (Object parameter : parameters) {
			map.put(i.toString(), parameter);
			++i;
		}
		return map;
	}

}
