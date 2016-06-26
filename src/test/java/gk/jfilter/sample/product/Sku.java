package gk.jfilter.sample.product;

public class Sku {
	
	private String code;
	private int price;

	public Sku() {	}
	
	public Sku(String code, int price) {
		this.code=code;
		this.price=price;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Sku {code=" + code + ", price=" + price + "}";
	}

}
