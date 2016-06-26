package gk.jfilter.test;

import static org.junit.Assert.assertEquals;
import gk.jfilter.JFilter;
import gk.jfilter.test.model.Animal;
import gk.jfilter.test.model.Cat;
import gk.jfilter.test.model.Dog;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

public class StringOperatorTest {
	final private static List<Animal> animals = new ArrayList<Animal>();
	@BeforeClass
	public static void  insertObjects() {
		animals.add(new Dog("dog1", 4, "black"));
		animals.add(new Cat("cat1", 4, "black"));
		
		animals.add(new Dog("dog2", 1, "white"));
		animals.add(new Cat("cat2", 3, "white"));
	}
	
	@Test
	public void testEQ() {
		JFilter<Animal> filter = new JFilter<Animal>(animals, Animal.class);
		
		List<Animal> fa = filter.filter("{'name':{'$sw':'?1'}}", "dog").out(new ArrayList<Animal>());
		assertEquals(2,fa.size());
	}
	
	@Test
	public void testNE() {
		JFilter<Animal> filter = new JFilter<Animal>(animals, Animal.class);
		
		List<Animal> fa = filter.filter("{'name':{'$ne':'?1'}}", "dog1").out(new ArrayList<Animal>());
		assertEquals(3,fa.size());
	}
	
	@Test
	public void testGT() {
		JFilter<Animal> filter = new JFilter<Animal>(animals, Animal.class);
		
		List<Animal> fa = filter.filter("{'name':{'$gt':'?1'}}", "dog1").out(new ArrayList<Animal>());
		assertEquals(1,fa.size());
	}
	
	@Test
	public void testGE() {
		JFilter<Animal> filter = new JFilter<Animal>(animals, Animal.class);
		
		List<Animal> fa = filter.filter("{'name':{'$ge':'?1'}}", "dog1").out(new ArrayList<Animal>());
		assertEquals(2,fa.size());
	}
	
	@Test
	public void testLT() {
		JFilter<Animal> filter = new JFilter<Animal>(animals, Animal.class);
		
		List<Animal> fa = filter.filter("{'name':{'$lt':'?1'}}", "dog1").out(new ArrayList<Animal>());
		assertEquals(2,fa.size());
	}
	
	@Test
	public void testLE() {
		JFilter<Animal> filter = new JFilter<Animal>(animals, Animal.class);
		
		List<Animal> fa = filter.filter("{'name':{'$le':'?1'}}", "dog1").out(new ArrayList<Animal>());
		assertEquals(3,fa.size());
	}
	
	@Test
	public void testSW() {
		JFilter<Animal> filter = new JFilter<Animal>(animals, Animal.class);
		
		List<Animal> fa = filter.filter("{'name':{'$sw':'?1'}}", "cat").out(new ArrayList<Animal>());
		assertEquals(2,fa.size());
	}
	
	@Test
	public void testEW() {
		JFilter<Animal> filter = new JFilter<Animal>(animals, Animal.class);
		
		List<Animal> fa = filter.filter("{'name':{'$ew':'?1'}}", "1").out(new ArrayList<Animal>());
		assertEquals(2,fa.size());
	}
	
	@Test
	public void testCTS() {
		JFilter<Animal> filter = new JFilter<Animal>(animals, Animal.class);
		
		List<Animal> fa = filter.filter("{'name.toString':{'$cts':'?1'}}", "a").out(new ArrayList<Animal>());
		assertEquals(2,fa.size());
	}
	
	@Test
	public void testBetween() {
		JFilter<Animal> filter = new JFilter<Animal>(animals, Animal.class);
		
		List<Animal> fa = filter.filter("{'$and':[{'name.length':{'$gt':'?1'}}, {'name.length':{'$lt':'?2'}}]}",4,10).out(new ArrayList<Animal>());
		assertEquals(0,fa.size());
	}
	
	@Test
	public void testBetween2() {
		JFilter<Animal> filter = new JFilter<Animal>(animals, Animal.class);
		
		List<Animal> fa = filter.filter("{'$and':[{'legs':{'$ge':'?1'}}, {'legs':{'$le':'?2'}}]}",2,4).out(new ArrayList<Animal>());
		assertEquals(3,fa.size());
	}
}

