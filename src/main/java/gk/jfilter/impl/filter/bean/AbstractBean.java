package gk.jfilter.impl.filter.bean;

import gk.jfilter.JFilterException;

import java.beans.IntrospectionException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractBean implements Bean {
	protected Method readMethod;
	protected Class<?> type;
	/** for map type bean */
	protected Class<?> keyType;
	/** for map, collection and array type bean */
	protected Class<?> valueType;
	
	protected Map<String, Bean> properties = new HashMap<String, Bean>();
	protected Bean parent;
	Map<String, Method> methods = new HashMap<String, Method>();

	protected AbstractBean(Class<?> type, Method readMethod, Bean parent) {
		this.type = type;
		this.readMethod = readMethod;
		this.parent = parent;
		
		addMethods(type);
	}

	protected void addMethods(Class<?> type) {
		Method[] methods = type.getMethods();
		for (Method m : methods) {
			if (isSupported(m)) {
				m.setAccessible(true);
				this.methods.put(m.getName(), m);
			}
		}
	}

	protected void addProperty(String name, Class<?> propertyType, Method readMethod) throws IntrospectionException {
		if (propertyType.isPrimitive() || Comparable.class.isAssignableFrom(propertyType)) {
			properties.put(name, new SimpleBean(propertyType, readMethod, this));
		} else if (propertyType.isArray()) {
			properties.put(name, new ArrayBean(propertyType, readMethod, this));
		} else if (Collection.class.isAssignableFrom(propertyType)) {
			properties.put(name, new CollectionBean(propertyType, readMethod, this));
		} else if (Map.class.isAssignableFrom(propertyType)) {
			properties.put(name, new MapBean(propertyType, readMethod, this));
		} else {
			properties.put(name, new ClassBean(propertyType, readMethod, this));
		}
	}

	public Object getValue(Object object) {
		try {
			return readMethod.invoke(object);
		} catch (Exception e) {
			throw new JFilterException("Error reading object value.", e);
		}
	}

	@Override
	public Class<?> getType() {
		return type;
	}
	
	@Override
	public Class<?> getKeyType() {
		return keyType;
	}
	
	@Override
	public Class<?> getValueType() {
		return valueType;
	}

	@Override
	public Bean getProperty(String propertyName) {
		// first try with getter method.
		Bean bean = properties.get(makeGetter(propertyName));
		if (bean == null) {
			if (bean == null) {
				// again try with isMethod
				bean = properties.get(makeIsMethod(propertyName));
			}
			// again try with direct method.
			bean = properties.get(propertyName);
			//try to load property.
			if (bean == null) {
				return addProperty(propertyName);
			}
		}
		return bean;
	}

	public Bean addProperty(String propertyName) {
		// first try with getter method
		Method method = methods.get(makeGetter(propertyName));
		if (method == null) {
			// again try with isMethod
			method = methods.get(makeIsMethod(propertyName));
		}
		if (method == null) {
			// again try direct method
			method = methods.get(propertyName);
		}

		if (method != null) {
			try {
				addProperty(method.getName(), method.getReturnType(), method);
			} catch (IntrospectionException e) {
				throw new JFilterException("Exception while adding property: " + propertyName, e);
			}
		} else {
			throw new JFilterException("Property: " + propertyName + " does not exist in the class: " + type);
		}

		return properties.get(method.getName());
	}

	protected String makeGetter(String property) {
		char[] pa = property.toCharArray();
		StringBuffer sb = new StringBuffer();
		sb.append("get");
		pa[0] = Character.toUpperCase(pa[0]);
		sb.append(pa);
		return sb.toString();
	}
	
	protected String makeIsMethod(String property) {
		char[] pa = property.toCharArray();
		StringBuffer sb = new StringBuffer();
		sb.append("is");
		pa[0] = Character.toUpperCase(pa[0]);
		sb.append(pa);
		return sb.toString();
	}

	/**
	 * only method supported by JFilter.
	 * 
	 * @return
	 */
	protected boolean isSupported(Method m) {
		if ((m.getReturnType() != Void.TYPE) && (m.getParameterTypes().length == 0)) {
			return true;
		}
		return false;
	}
	
}
