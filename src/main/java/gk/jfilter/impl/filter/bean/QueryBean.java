package gk.jfilter.impl.filter.bean;

import java.beans.IntrospectionException;



/**
 * Stores properties details of a query class.
 * 
 * @author Kamran Ali Khan (khankamranali@gmail.com)
 * 
 * @param <T>
 */
public class QueryBean extends AbstractBean {
	
	
	public QueryBean(Class<?> type) throws IntrospectionException {
		super(type, null, null);
	}
}
