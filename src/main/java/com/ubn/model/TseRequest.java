package com.ubn.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "RRN","stan","acctno", "amount", "txndate"})
public class TseRequest {

	@JsonProperty("RRN")
	private String rrn;
	@JsonProperty("stan")
	private String stan;
	@JsonProperty("acctno")
	private String acctno;
	@JsonProperty("amount")
	private String amount;
	@JsonProperty("txndate")
	private String txndate;
	
	public String getRrn() {
		return rrn;
	}
	public void setRrn(String rrn) {
		this.rrn = rrn;
	}
	public String getStan() {
		return stan;
	}
	public void setStan(String stan) {
		this.stan = stan;
	}
	public String getAcctno() {
		return acctno;
	}
	public void setAcctno(String acctno) {
		this.acctno = acctno;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getTxndate() {
		return txndate;
	}
	public void setTxndate(String txndate) {
		this.txndate = txndate;
	}
}
