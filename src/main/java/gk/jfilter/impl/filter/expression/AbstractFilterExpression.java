package gk.jfilter.impl.filter.expression;

import gk.jfilter.impl.filter.bean.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * A Filter can have expressions and an expression can have filters.
 * 
 * @author Kamran Ali Khan (khankamranali@gmail.com)
 * 
 * @param <T>
 */
public abstract class AbstractFilterExpression implements FilterExpression {

	protected String filterKey;
	protected Bean bean;
	final private List<FilterExpression> expressionsList = new ArrayList<FilterExpression>(5);
	/**
	 * array is used for performance reason, during the test with 1 million
	 * records it is found that array iteration is roughly 2 times faster than
	 * List.
	 */
	FilterExpression[] expressions = null;
	
	@Override
	public String getFilterKey() {
		return filterKey;
	}
	
	@Override
	public Bean getBean() {
		return bean;
	}

	public void addExpression(FilterExpression expression) {
		expressionsList.add(expression);
		expressions = expressionsList.toArray(new FilterExpression[expressionsList.size()]);
	}
	
	FilterExpression[] getExpressions() {
		return expressions;
	}
}
