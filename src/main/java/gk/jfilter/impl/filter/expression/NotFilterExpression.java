package gk.jfilter.impl.filter.expression;

import gk.jfilter.JFilterException;
import gk.jfilter.impl.filter.bean.Bean;

public class NotFilterExpression extends AbstractFilterExpression {

	public NotFilterExpression(String filterKey, Bean bean) {
		this.filterKey = filterKey;
		this.bean=bean;
	}

	public boolean eval(Object object) {
		if(object==null) {
			return false;
		}
		if (expressions.length != 1) {
			new JFilterException("$not operator must have only one argument e.g. {'$not':[{'color':'?1'}]}.");
		}
		
		return !expressions[0].eval(object);
	}

}
