package com.clikfin.clikfinapplication.externalRequests.Request;

import com.google.gson.annotations.SerializedName;

public class DocumentReportRequest {

	@SerializedName("customer_id")
	private int customerId;

	@SerializedName("loan_id")
	private int loanId;

	public int getCustomerId(){
		return customerId;
	}

	public int getLoanId(){
		return loanId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}
}