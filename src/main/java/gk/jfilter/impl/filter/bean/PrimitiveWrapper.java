package gk.jfilter.impl.filter.bean;

import java.util.HashMap;
import java.util.Map;

public class PrimitiveWrapper {
	private static final Map<Class<?>, Class<?>> WRAPPERS;
	static {
		WRAPPERS = new HashMap<Class<?>, Class<?>>();
		WRAPPERS.put(boolean.class, Boolean.class);
		WRAPPERS.put(byte.class, Byte.class);
		WRAPPERS.put(char.class, Character.class);
		WRAPPERS.put(double.class, Double.class);
		WRAPPERS.put(float.class, Float.class);
		WRAPPERS.put(int.class, Integer.class);
		WRAPPERS.put(long.class, Long.class);
		WRAPPERS.put(short.class, Short.class);
		WRAPPERS.put(void.class, Void.class);
	}

	@SuppressWarnings("unchecked")
	public static <T> Class<T> wrap(Class<T> c) {
		return c.isPrimitive() ? (Class<T>) WRAPPERS.get(c) : c;
	}
}
