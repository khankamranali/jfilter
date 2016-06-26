package gk.jfilter.test;

import static org.junit.Assert.assertEquals;
import gk.jfilter.JFilter;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

public class StringTest {
	final private static List<String> animals = new ArrayList<String>();
	
	@BeforeClass
	public static void  insertObjects() {				
		animals.add("dog");
		animals.add("cat");
		animals.add("rat");
		animals.add("Lion");
		animals.add("tiger");
		animals.add(" elephant ");
	}

	@Test
	public void testToString() {	
		JFilter<String> filter = new JFilter<String>(animals, String.class);
		
		List<String> fa = filter.filter("{'toString':{'$cts':'?1'}}", "a").out(new ArrayList<String>());
		assertEquals(3,fa.size());
	}
	
	
	@Test
	public void testToUpperLower() {	
		JFilter<String> filter = new JFilter<String>(animals, String.class);

		assertEquals(1,filter.filter("{'toString.toUpperCase':'?1'}", "LION").out(new ArrayList<String>()).size());
		assertEquals(1,filter.filter("{'toString.toLowerCase':'?1'}", "lion").out(new ArrayList<String>()).size());
	}
	
	@Test
	public void testTrim() {	
		JFilter<String> filter = new JFilter<String>(animals, String.class);

		assertEquals(1,filter.filter("{'trim':'?1'}", "elephant").out(new ArrayList<String>()).size());
	}
	
	@Test
	public void testIsEmpty() {	
		JFilter<String> filter = new JFilter<String>(animals, String.class);

		assertEquals(6,filter.filter("{'empty':'?1'}", false).out(new ArrayList<String>()).size());
		assertEquals(0,filter.filter("{'empty':'?1'}", true).out(new ArrayList<String>()).size());
	}
	
	@Test
	public void testLength() {	
		JFilter<String> filter = new JFilter<String>(animals, String.class);

		assertEquals(3,filter.filter("{'length':'?1'}", 3).out(new ArrayList<String>()).size());
		assertEquals(1,filter.filter("{'length':'?1'}", 4).out(new ArrayList<String>()).size());
	}
}
