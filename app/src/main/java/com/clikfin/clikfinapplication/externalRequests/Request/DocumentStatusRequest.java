package com.clikfin.clikfinapplication.externalRequests.Request;

import com.google.gson.annotations.SerializedName;

public class DocumentStatusRequest {

	@SerializedName("document_type_id")
	private int documentTypeId;

	@SerializedName("customer_id")
	private int customerId;

	@SerializedName("loan_id")
	private int loanId;

	@SerializedName("status")
	private String status;

	public int getDocumentTypeId(){
		return documentTypeId;
	}

	public int getCustomerId(){
		return customerId;
	}

	public int getLoanId(){
		return loanId;
	}

	public String getStatus(){
		return status;
	}

	public void setDocumentTypeId(int documentTypeId) {
		this.documentTypeId = documentTypeId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}