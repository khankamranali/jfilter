package gk.jfilter.impl.mr.m;

import gk.jfilter.impl.filter.bean.Bean;

import java.util.Collection;

public class CollectionMapper extends AbstractMapper {

	CollectionMapper(Bean bean, Mapper mapper) {
		super(bean, mapper);
	}

	CollectionMapper(Bean bean) {
		super(bean, null);
	}

	@Override
	public <O, T> void map(O object, Collection<T> list) {
		if (object == null) {
			return;
		}

		Collection<O> c = (Collection<O>) bean.getValue(object);
		for (O o : c) {
			mapOne(o, list);
		}
	}

	@Override
	public Class<?> getType() {
		if (mapper != null) {
			return mapper.getType();
		} else {
			return bean.getValueType();
		}
	}

}
