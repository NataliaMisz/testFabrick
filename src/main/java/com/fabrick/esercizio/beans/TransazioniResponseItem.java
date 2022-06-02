package com.fabrick.esercizio.beans;

import java.util.List;
import java.util.Map;

public class TransazioniResponseItem {
	
	private String status;
	private String[] errors;
	private Map<String,List<Transaction>> payload;
	
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
	public Map<String, List<Transaction>> getPayload() {
		return payload;
	}
	public void setPayload(Map<String, List<Transaction>> payload) {
		this.payload = payload;
	}

}
