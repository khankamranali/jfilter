package gk.jfilter.test.model;

public class Dog extends Animal {

	private final String name;
	private final int legs;
	private final String color;
	private final String sound = "WOOF!!!";

	public Dog(String name, int i, String color) {
		this.name = name;
		this.legs = i;
		this.color = color;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getLegs() {
		return legs;
	}

	@Override
	public String getSound() {
		return sound;
	}

	@Override
	public String getColor() {
		return color;
	}

	@Override
	public String type() {
		return "dog";
	}

	@Override
	public String toString() {
		return getName() + " is a dog with " + getLegs() + " legs and " + getColor() + " in color says " + getSound();
	}

}
