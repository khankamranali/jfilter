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

public class Random {
	
	final private static List<Animal> animals = new ArrayList<Animal>();
	
	@BeforeClass
	public static void  insertObjects() {
		//add dog family
		Animal dog1 = new Dog("dog1", 4, "black");
		Animal dog2 = new Dog("dog2", 4, "white");
		Animal dog3 = new Dog("dog3", 4, "black");
		Animal dog4 = new Dog("dog4", 4, null);
		
		dog1.addChild(dog2);
		dog1.addChild(dog3);
		dog1.addChild(dog4);
				
		animals.add(dog1);
		animals.add(dog2);
		animals.add(dog3);
		animals.add(dog4);
		
		// add cat family
		Animal cat1 = new Cat("cat1", 4, "white");
		Animal cat2 = new Cat("cat2", 4, "white");
		Animal cat3 = new Cat("cat3", 4, "black");
		Animal cat4 = new Cat("cat4", 4, "white");
		
		cat1.addChild(cat2);
		cat1.addChild(cat3);
		cat1.addChild(cat4);
		
		animals.add(cat1);
		animals.add(cat2);
		animals.add(cat3);
		animals.add(cat4);
	}
	
	@Test
	public void testNull() {
		JFilter<Animal> filter = new JFilter<Animal>(animals, Animal.class);
		assertEquals(1,filter.filter("{'color':'?1'}", (String)null).out(new ArrayList<Animal>()).size());
	}
	
	@Test
	public void testMother() {
		JFilter<Animal> filter = new JFilter<Animal>(animals, Animal.class);
		
		assertEquals(3,filter.filter("{'mother.color':'?1'}", "black").out(new ArrayList<Animal>()).size());
		assertEquals(3,filter.filter("{'mother.color':'?1'}", "white").out(new ArrayList<Animal>()).size());
	}
	
	@Test
	public void testNot() {
		JFilter<Animal> filter = new JFilter<Animal>(animals, Animal.class);
		assertEquals(5,filter.filter("{'$not':[{'mother.color':'?1'}]}", "white").out(new ArrayList<Animal>()).size());
		assertEquals(5,filter.filter("{'$not':[{'mother.color':'?1'}]}", "black").out(new ArrayList<Animal>()).size());
	}
	
	@Test
	public void testNull2() {
		JFilter<Animal> filter = new JFilter<Animal>(animals, Animal.class);
		assertEquals(4,filter.filter("{'mother.color':'?1'}", (String)null).out(new ArrayList<Animal>()).size());
	}
}
