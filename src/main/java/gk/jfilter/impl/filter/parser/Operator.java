package gk.jfilter.impl.filter.parser;

import gk.jfilter.JFilterException;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Operators = all the operators 
 * Comparators = all the operators excluding joins
 * Joins = $and and $or
 * 
 * @author Kamran Ali Khan (khankamranali@gmail.com)
 * 
 */
public enum Operator {

	$gt, $lt, $le, $eq, $ne, $ge, $sw, $ew, $cts, $in, $nin, $size, $exist, $and, $or, $not;

	public static final EnumSet<Operator> COMPARATOR = EnumSet.range($gt, $exist);
	public static final EnumSet<Operator> JOIN = EnumSet.of($and, $or, $not);
	public static final EnumSet<Operator> ALL = EnumSet.range($gt, $not);

	final private static Map<String, Operator> operators = new HashMap<String, Operator>();
	final private static Map<String, Operator> comparators = new HashMap<String, Operator>();
	final private static Map<String, Operator> joins = new HashMap<String, Operator>();

	static {
		for (Operator o : Operator.ALL) {
			operators.put(o.toString(), o);
		}

		for (Operator c : Operator.COMPARATOR) {
			comparators.put(c.toString(), c);
		}

		for (Operator j : Operator.JOIN) {
			joins.put(j.toString(), j);
		}
	}

	public static Operator operatorOf(String string) {
		Operator operator = operators.get(string);
		if (operator == null) {
			throw new JFilterException("Operator " + string + " does not exists.");
		}
		return operator;
	}

	public static boolean isOperator(String s) {
		if (operators.get(s) == null) {
			return false;
		}
		return true;
	}

	public static boolean isComparator(String s) {
		if (comparators.get(s) == null) {
			return false;
		}
		return true;
	}

	public static boolean isJoin(String s) {
		if (joins.get(s) == null) {
			return false;
		}
		return true;
	}
	
	public static boolean isComparator(Operator o) {
		return !(o==Operator.$and || o==Operator.$or || o==Operator.$not);
	}

	public static boolean isJoin(Operator o) {
		return (o==Operator.$and || o==Operator.$or || o==Operator.$not);
	}


	public static void main(String args[]) {
		System.out.println(Operator.operatorOf("$eq"));
	}

}
