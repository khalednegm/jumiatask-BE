package com.jumiatask.dtos;

public class CustomerDTO {
	Integer customerId;
	String customerName;
	String phone;
	
	
	public Integer geCustomertId() {
		return customerId;
	}
	public void setCustomertId(Integer id) {
		this.customerId = id;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
