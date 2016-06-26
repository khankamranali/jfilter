package gk.jfilter.sample.salesorder;

import java.util.Date;
import java.util.List;

public class SalesOrder {
	
	long id;
	String status;
	Date orderDate;
	String customerId;
	String customerName;
	String salesRepId;
	String salesRepName;
	Date shipDate;
	float taxAmount;
	double totalAmount;
	Address shippingAddress;
	Address billingAddress;
	List<LineItem> lineItems;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getSalesRepId() {
		return salesRepId;
	}
	public void setSalesRepId(String salesRepId) {
		this.salesRepId = salesRepId;
	}
	public String getSalesRepName() {
		return salesRepName;
	}
	public void setSalesRepName(String salesRepName) {
		this.salesRepName = salesRepName;
	}
	public Date getShipDate() {
		return shipDate;
	}
	public void setShipDate(Date shipDate) {
		this.shipDate = shipDate;
	}
	public float getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(float taxAmount) {
		this.taxAmount = taxAmount;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Address getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	public Address getBillingAddress() {
		return billingAddress;
	}
	public void setBillingAddress(Address billingAddress) {
		this.billingAddress = billingAddress;
	}
	public List<LineItem> getLineItems() {
		return lineItems;
	}
	public void setLineItems(List<LineItem> lineItems) {
		this.lineItems = lineItems;
	}
	
	@Override
	public String toString() {
		return "SalesOrder {id=" + id + ", status=" + status + ", orderDate=" + orderDate + ", customerId="
				+ customerId + ", customerName=" + customerName + ", salesRepId=" + salesRepId + ", salesRepName="
				+ salesRepName + ", shipDate=" + shipDate + ", taxAmount=" + taxAmount + ", totalAmount=" + totalAmount
				+ ", shippingAddress=" + shippingAddress + ", billingAddress=" + billingAddress + "}";
	}

}
