package gk.jfilter.impl.filter.bean;

import gk.jfilter.JFilterException;

import java.beans.IntrospectionException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class CollectionBean extends AbstractBean {

	public CollectionBean(Class<?> type, Method readMethod, Bean parent) throws IntrospectionException {
		super(type, readMethod, parent);

		Type t = readMethod.getGenericReturnType();
		if (t instanceof ParameterizedType) {
			Type actualType = ((ParameterizedType) t).getActualTypeArguments()[0];
			if (actualType instanceof Class) {
				valueType = (Class<?>) actualType;
				if (valueType.isPrimitive() || Comparable.class.isAssignableFrom(valueType)) {
					properties.put("get", new SimpleBean(valueType, this));
				} else {
					addMethods(valueType);
				}
			}
		} else {
			throw new JFilterException("Generic type information is required for Collection and Map types properties.");
		}
	}
}
