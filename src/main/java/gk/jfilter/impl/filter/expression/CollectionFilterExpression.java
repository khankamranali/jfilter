package gk.jfilter.impl.filter.expression;

import java.util.Collection;

import gk.jfilter.impl.filter.bean.Bean;

public class CollectionFilterExpression extends AbstractFilterExpression {

	public CollectionFilterExpression(String filterKey, Bean bean) {
		this.filterKey = filterKey;
		this.bean = bean;
	}

	/**
	 * If any of the collection element matches the filter it is true.
	 */
	public boolean eval(Object object) {
		if(object==null) {
			return false;
		}
		
		Collection<?> values = (Collection<?>) bean.getValue(object);
		for (Object value : values) {
			if (and(value) == true) {
				return true;
			}
		}
		return false;
	}

	private boolean and(Object object) {
		for (FilterExpression exp : expressions) {
			if (exp.eval(object) == false) {
				return false;
			}
		}
		return true;
	}

}
