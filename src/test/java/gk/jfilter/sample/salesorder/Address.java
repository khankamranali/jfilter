package gk.jfilter.sample.salesorder;

public class Address {
	
	String country;
	String state;
	String city;
	String pin;
	String[] lines;
	
	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getPin() {
		return pin;
	}


	public void setPin(String pin) {
		this.pin = pin;
	}

	public String[] getLines() {
		return lines;
	}


	public void setLines(String[] lines) {
		this.lines = lines;
	}


	@Override
	public String toString() {
		return "Address {country=" + country + ", state=" + state + ", city=" + city + ", pin=" + pin + ", lines="
				+ lines+"}";
	}

}
