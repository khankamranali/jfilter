package gk.jfilter.impl.filter.expression;

import gk.jfilter.impl.filter.bean.Bean;
import gk.jfilter.impl.filter.comparator.SimpleTypeComparator;
import gk.jfilter.impl.filter.parser.Operator;

public class SimpleFilterExpression extends AbstractFilterExpression implements FilterExpression {

	final private SimpleTypeComparator comparator = new SimpleTypeComparator();
	private Operator operator;
	/** for $in and $nin array of values otherwise single value. */
	private Comparable<?>[] filterValues;

	public SimpleFilterExpression(String filterKey, Object[] filterValues, Operator operator, Bean bean) {
		this.filterKey = filterKey;
		this.operator = operator;
		this.bean = bean;
		setFilterValues(filterValues);
	}

	private void setFilterValues(Object[] filterValues) {
		this.filterValues = new Comparable<?>[filterValues.length];
		int i = 0;
		for (Object object : filterValues) {
			this.filterValues[i] = (Comparable<?>) object;
			++i;
		}
	}

	public boolean eval(Object object) {
		
		if(object==null) {
			return false;
		}
		
		return comparator.compare((Comparable) bean.getValue(object), this.filterValues, this.operator);
	}

}
