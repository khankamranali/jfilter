package gk.jfilter.sample.simple;

import gk.jfilter.JFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Simple sample program to demostrate how to use JFilter.
 * 
 * @author Kamran Ali Khan (khankamranali@gmail.com)
 * 
 */
public class SimpleFilterMain {

	public static final List<String> animals = Arrays.asList("dog", "cat", "rat", "Lion", "tiger", " elephant ");

	/**
	 * Filter method in JFilter filters the collection and out method copies the
	 * output of filter into a given collection.
	 */
	public static void main(String[] args) {
		JFilter<String> filter = new JFilter<String>(animals, String.class);

		/** filter has trim method of String class */
		/** select strings in list where trim function of string returns "elephant" */
		System.out.println(filter.filter("{'trim':'?1'}", "elephant").out(new ArrayList<String>()));
		
		/** filter has empty property ( isEmpty method) of String class */
		/** select empty strings in the list. */
		System.out.println(filter.filter("{'empty':'?1'}", true).out(new ArrayList<String>()));
		
		/** filter has length method of String class */
		/** select strings of length 3 */
		System.out.println(filter.filter("{'length':'?1'}", 3).out(new ArrayList<String>()));
		
		/** filter has toUpperCase method of String class */
		/** Select strings where toUpperCase function returns "LION". */
		System.out.println(filter.filter("{'toUpperCase':'?1'}", "LION").out(new ArrayList<String>()));
		
		/** filter has toLowerCase method of String class */
		/** Select strings where toLowerCase function returns "LION". */
		System.out.println(filter.filter("{'toLowerCase':'?1'}", "lion").out(new ArrayList<String>()));
		
		/** filter has class property (getClass method) of String super class Object which has name property */
		/** Select strings with class name equals to "java.lang.String".*/
		System.out.println(filter.filter("{'class.name':'?1'}", "java.lang.String").out(new ArrayList<String>()));
	}
}
