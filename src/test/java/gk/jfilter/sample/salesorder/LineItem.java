package gk.jfilter.sample.salesorder;

import java.util.Map;

public class LineItem {
	
	short line;
	String productId;
	int quantity; 
	float unitPrice;
	Map<Tax, Float> taxes;
	float lineAmount;
	float discount;
	
	public short getLine() {
		return line;
	}
	public void setLine(short line) {
		this.line = line;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public float getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Map<Tax, Float> getTaxes() {
		return taxes;
	}
	public void setTaxes(Map<Tax, Float> taxes) {
		this.taxes = taxes;
	}
	public float getLineAmount() {
		return lineAmount;
	}
	public void setLineAmount(float lineAmount) {
		this.lineAmount = lineAmount;
	}
	public float getDiscount() {
		return discount;
	}
	public void setDiscount(float discount) {
		this.discount = discount;
	}
	@Override
	public String toString() {
		return "LineItem {line=" + line + ", productId=" + productId + ", quantity=" + quantity + ", unitPrice="
				+ unitPrice + ", taxAmount=" + taxes + ", lineAmount=" + lineAmount
				+ ", discount=" + discount + "}";
	}
	
}
