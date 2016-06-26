package gk.jfilter.impl.mr.m;

import gk.jfilter.impl.filter.bean.Bean;

import java.util.Arrays;
import java.util.Collection;

/**
 * Builds Mapper objects from String e.g. "skus.price".
 * 
 * @author Kamran Ali Khan (khankamranali@gmail.com)
 * 
 */
public class MapParser {

	public static Mapper parse(Bean bean, String property) {
		String[] properties = property.split("\\.");
		return parseDots(bean, properties);
	}

	private static Mapper parseDots(Bean bean, String[] properties) {
		String nextProperty = properties[0];
		Bean nextBean = bean.getProperty(nextProperty);

		Mapper mapper;
		if (properties.length > 1) {
			// recursion
			mapper = createMapper(nextBean, parseDots(nextBean, Arrays.copyOfRange(properties, 1, properties.length)));
		} else {
			mapper = createMapper(nextBean, null);
		}
		return mapper;
	}

	private static Mapper createMapper(Bean bean, Mapper mapper) {
		if (Collection.class.isAssignableFrom(bean.getType())) {
			return new CollectionMapper(bean, mapper);
		} else if (bean.getType().isArray()) {
			return new ArrayMapper(bean, mapper);
		} else {
			return new ClassMapper(bean, mapper);
		}
	}

}
