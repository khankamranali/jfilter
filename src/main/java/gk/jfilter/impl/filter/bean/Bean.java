package gk.jfilter.impl.filter.bean;

public interface Bean {
	Class<?> getType();

	/** for map type bean */
	Class<?> getKeyType();

	/** for map, collection and array type bean */
	Class<?> getValueType();

	Bean getProperty(String propertyName);

	Object getValue(Object object);

}
