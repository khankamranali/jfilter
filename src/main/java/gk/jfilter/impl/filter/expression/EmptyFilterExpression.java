package gk.jfilter.impl.filter.expression;

import gk.jfilter.impl.filter.bean.Bean;

public class EmptyFilterExpression extends AbstractFilterExpression {

	public EmptyFilterExpression(String filterKey, Bean bean) {
		this.filterKey = filterKey;
		this.bean=bean;
	}

	public boolean eval(Object object) {
		if(object==null) {
			return false;
		}
		return expressions[0].eval(object);
	}

}
