package gk.jfilter.impl.filter.json;

import gk.jfilter.JFilterException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class JacksonJsonImpl implements Json {
	final private static ObjectMapper mapper = new ObjectMapper();
	
	static {
		mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
	}
	
	public <T> byte[] toJson(T t) {
		try {
			return mapper.writeValueAsBytes(t);
		} catch (JsonGenerationException e) {
			throw new JFilterException(e);
		} catch (JsonMappingException e) {
			throw new JFilterException(e);
		} catch (IOException e) {
			throw new JFilterException(e);
		}
	}

	public Map<String, ?> toMap(byte[] json) {
		try {
			return mapper.readValue(json, Map.class);
		} catch (JsonParseException e) {
			throw new JFilterException(e);
		} catch (JsonMappingException e) {
			throw new JFilterException(e);
		} catch (IOException e) {
			throw new JFilterException(e);
		}
	}

	public List<Map<?,?>> toList(byte[] json) {
		try {
			return mapper.readValue(json, List.class);
		} catch (JsonParseException e) {
			throw new JFilterException(e);
		} catch (JsonMappingException e) {
			throw new JFilterException(e);
		} catch (IOException e) {
			throw new JFilterException(e);
		}
	}

}
