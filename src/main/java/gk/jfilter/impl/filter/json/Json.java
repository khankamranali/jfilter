package gk.jfilter.impl.filter.json;

import java.util.List;
import java.util.Map;

public interface Json {
	<T> byte[] toJson(T t);

	Map<String, ?> toMap(byte[] json);
	
	List<Map<?,?>> toList(byte[] json);
}
