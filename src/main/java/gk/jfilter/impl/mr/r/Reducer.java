package gk.jfilter.impl.mr.r;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;

public class Reducer {

	public static <T extends Comparable<T>> T min(Iterable<T> iterable) {
		Iterator<T> itr = iterable.iterator();
		T min = itr.next();

		while (itr.hasNext()) {
			T next = itr.next();
			if (next.compareTo(min) < 0)
				min = next;
		}
		return min;
	}

	public static <T extends Comparable<T>> T max(Iterable<T> iterable) {
		Iterator<T> itr = iterable.iterator();
		T max = itr.next();

		while (itr.hasNext()) {
			T next = itr.next();
			if (next.compareTo(max) > 0)
				max = next;
		}
		return max;
	}

	public static <T extends Number> T sum(Iterable<T> iterable) {
		Number sum = null;
		for (T n : iterable) {
			if (n instanceof Integer) {
				sum = (Integer) sum + (Integer) n;
			} else if (n instanceof Long) {
				sum = sum==null?new Long(0): (Long)sum + (Long)n;
			} else if (n instanceof Float) {
				sum = sum==null?new Float(0):(Float) sum + (Float) n;
			} else if (n instanceof Double) {
				sum = sum==null?new Double(0):(Double) sum + (Double) n;
			} else if (n instanceof BigInteger) {
				sum = sum==null?new BigInteger("0"):((BigInteger) sum).add((BigInteger) n);
			} else if (n instanceof BigDecimal) {
				sum = sum==null?new BigDecimal(0):((BigDecimal) sum).add((BigDecimal) n);
			}
		}

		return (T) sum;
	}
	
	public static <T> int count(Iterable<T> iterable) {
		int count=0;
		for(T t: iterable) {
			count++;
		}
		return count;
	}
}
