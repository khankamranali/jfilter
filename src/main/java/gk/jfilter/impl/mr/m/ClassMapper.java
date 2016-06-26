package gk.jfilter.impl.mr.m;

import gk.jfilter.impl.filter.bean.Bean;

import java.util.Collection;

public class ClassMapper extends AbstractMapper {
	
	ClassMapper(Bean bean, Mapper mapper) {
		super(bean, mapper);
	}

	ClassMapper(Bean bean) {
		super(bean, null);
	}
	
	@Override
	public <O, T> void map(O object, Collection<T> list) {
		if (object == null) {
			return;
		}

		Object po = bean.getValue(object);
		mapOne(po, list);
	}
	
	@Override
	public Class<?> getType() {
		if (mapper != null) {
			return mapper.getType();
		} else {
			return bean.getType();
		}
	}
}
