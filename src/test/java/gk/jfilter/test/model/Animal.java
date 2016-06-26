package gk.jfilter.test.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Animal {
	List<Animal> children = new ArrayList<Animal>();
	Animal mother = null;
	
	public void addChild(Animal child) {
		children.add(child);
		child.mother=this;
	}

	public List<Animal> getChildren() {
		return children;
	}
		
	public Animal getMother() {
		return mother;
	}
	
	public abstract String getName();

	public abstract int getLegs();

	public abstract String getSound();

	public abstract String getColor();

	public abstract String type();
}
