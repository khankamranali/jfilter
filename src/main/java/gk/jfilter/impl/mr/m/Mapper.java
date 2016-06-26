package gk.jfilter.impl.mr.m;

import java.util.Collection;

public interface Mapper {
	<O, T> void map(O object, Collection<T> list);

	Class<?> getType();
}
