package gk.jfilter.impl.mr.m;

import gk.jfilter.impl.filter.bean.Bean;

import java.util.Collection;

public abstract class AbstractMapper implements Mapper {
	protected Bean bean;
	protected Mapper mapper;

	protected AbstractMapper(Bean bean, Mapper mapper) {
		this.bean = bean;
		this.mapper = mapper;
	}
	
	
	protected <O, T> void mapOne(O o, Collection<T> list) {
		if (mapper != null) {
			mapper.map(o, list);
		} else {
			list.add((T) o);
		}
	}

}
