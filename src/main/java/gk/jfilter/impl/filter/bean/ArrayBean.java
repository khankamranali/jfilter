package gk.jfilter.impl.filter.bean;

import java.beans.IntrospectionException;
import java.lang.reflect.Method;

public class ArrayBean extends AbstractBean {

	public ArrayBean(Class<?> type, Method readMethod, Bean parent) throws IntrospectionException {
		super(type, readMethod, parent);
		valueType = type.getComponentType(); 
		if (valueType.isPrimitive() || Comparable.class.isAssignableFrom(valueType)) {
			properties.put("get", new SimpleBean(type.getComponentType(), this));
		}
	}
}
