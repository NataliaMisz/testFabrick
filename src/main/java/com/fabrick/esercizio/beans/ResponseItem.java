package com.fabrick.esercizio.beans;

import java.util.Map;

public class ResponseItem {
	
	private String status;
	private String[] errors;
	private Map<String,String> payload;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String[] getErrors() {
		return errors;
	}
	public void setErrors(String[] errors) {
		this.errors = errors;
	}
	public Map<String, String> getPayload() {
		return payload;
	}
	public void setPayload(Map<String, String> payload) {
		this.payload = payload;
	}
	
}
