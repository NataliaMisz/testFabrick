package com.fabrick.esercizio.beans;

import java.util.Map;

public class Creditor {
	
	private String name;
	private Map<String, String> account;
	private Map<String, String> address;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<String, String> getAccount() {
		return account;
	}
	public void setAccount(Map<String, String> account) {
		this.account = account;
	}
	public Map<String, String> getAddress() {
		return address;
	}
	public void setAddress(Map<String, String> address) {
		this.address = address;
	}
	
}
