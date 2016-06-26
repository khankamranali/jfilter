package gk.jfilter.impl.filter.bean;

import gk.jfilter.JFilterException;

import java.beans.IntrospectionException;
import java.lang.reflect.Method;

public class SimpleBean extends AbstractBean {

	public SimpleBean(Class<?> type, Method readMethod, Bean parent) throws IntrospectionException {
		super(type, readMethod, parent);
	}
	
	public SimpleBean(Class<?> type, Bean parent) throws IntrospectionException {
		super(type, null, parent);
	}
	
	public Object getValue(Object object) {
		try {
			if(readMethod!=null) {
				return readMethod.invoke(object);
			}
		} catch (Exception e) {
			throw new JFilterException("Error reading object value.", e);
		}
		
		return object;
	}
}
