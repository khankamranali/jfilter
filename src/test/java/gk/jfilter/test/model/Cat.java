package gk.jfilter.test.model;




public class Cat extends Animal {

	private final String name;
	private final int legs;
	private final String color;
	private final String sound = "MEAOW!!!";

	public Cat(String name, int i, String color) {
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
		return "cat";
	}
		
	@Override
	public String toString() {
		return getName()+" is a cat with " + getLegs() + " legs and " + getColor()
				+ " in color says " + getSound();
	}
}
