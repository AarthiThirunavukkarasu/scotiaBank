package com.scotiaBank.ScotiaBank.dto;

import java.util.List;

import org.springframework.lang.Nullable;

public class Customer {
	@Nullable
	private long customerID;
	
	private String firstName;
	
	private String lastName;
	
	private String emailId;
	
	private String sin;
	
	private String address;
	
	private String postalCode;
	
	private String phoneNumber;
	@Nullable
	private List<Integer> accounts;
	 
	 
	
	public long getCustomerID() {
		return customerID;
	}

	public void setCustomerID(long customerID) {
		this.customerID = customerID;
	}

	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getSin() {
		return sin;
	}
	public void setSin(String sin) {
		this.sin = sin;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public List<Integer> getAccounts() {
		return accounts;
	}
	public void setAccounts(List<Integer> accounts) {
		this.accounts = accounts;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	@Override
	public String toString() {
		return "Customer [customerID=" + customerID + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", emailId=" + emailId + ", sin=" + sin + ", address=" + address + ", postalCode=" + postalCode
				+ ", phoneNumber=" + phoneNumber + ", accounts=" + accounts + "]";
	}
	
    
	 
}
