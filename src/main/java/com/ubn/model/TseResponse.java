package com.ubn.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "RRN","stan","acctno", "amount", "txndate", "responseCode"})
public class TseResponse  extends TseRequest{

	@JsonProperty("responseCode")
	private String responseCode;
	
	public TseResponse() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
}
