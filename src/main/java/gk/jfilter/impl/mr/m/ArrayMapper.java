package gk.jfilter.impl.mr.m;

import gk.jfilter.impl.filter.bean.Bean;

import java.util.Collection;

public class ArrayMapper extends AbstractMapper {

	ArrayMapper(Bean bean, Mapper mapper) {
		super(bean, mapper);
	}

	private ArrayMapper(Bean bean) {
		super(bean, null);
	}

	@Override
	public <O, T> void map(O object, Collection<T> list) {
		if (object == null) {
			return;
		}

		O[] os = (O[]) bean.getValue(object);

		for (O o : os) {
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
