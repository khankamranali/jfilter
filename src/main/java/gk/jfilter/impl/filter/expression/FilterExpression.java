package gk.jfilter.impl.filter.expression;

import gk.jfilter.impl.filter.bean.Bean;

/**
 * 
 * 
 * @author Kamran Ali Khan (khankamranali@gmail.com)
 * 
 * @param <T>
 */
public interface FilterExpression {

	String getFilterKey();
	
	Bean getBean();

	void addExpression(FilterExpression expression);

	boolean eval(Object object);
}
