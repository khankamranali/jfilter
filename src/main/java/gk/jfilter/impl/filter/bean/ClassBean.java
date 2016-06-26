package gk.jfilter.impl.filter.bean;

import java.beans.IntrospectionException;
import java.lang.reflect.Method;

public class ClassBean extends AbstractBean {
	
	public ClassBean(Class<?> type, Method readMethod, Bean parent) throws IntrospectionException {
		super(type, readMethod, parent);
	}
}
