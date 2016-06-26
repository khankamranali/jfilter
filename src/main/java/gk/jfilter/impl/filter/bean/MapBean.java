package gk.jfilter.impl.filter.bean;

import gk.jfilter.JFilterException;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

public class MapBean extends AbstractBean {
	
	public MapBean(Class<?> type, Method readMethod, Bean parent) throws IntrospectionException {
		super(type, readMethod, parent);
		
		Type t = readMethod.getGenericReturnType();
		if (t instanceof ParameterizedType) {
			keyType = (Class<?>) ((ParameterizedType) t).getActualTypeArguments()[0];
			valueType = (Class<?>) ((ParameterizedType) t).getActualTypeArguments()[1];
			
			PropertyDescriptor pds[] = Introspector.getBeanInfo(Map.Entry.class).getPropertyDescriptors();
			
			for (PropertyDescriptor pd : pds) {
				if (pd.getReadMethod() != null && "key".equals(pd.getName())) {
					addProperty(pd.getName(), keyType, pd.getReadMethod());
				} else if (pd.getReadMethod() != null && "value".equals(pd.getName())) {
					addProperty(pd.getName(), valueType, pd.getReadMethod());
				}
			}
			
		} else {
			throw new JFilterException("Generic type information is required for Collection and Map types properties.");
		}
	}	
}
