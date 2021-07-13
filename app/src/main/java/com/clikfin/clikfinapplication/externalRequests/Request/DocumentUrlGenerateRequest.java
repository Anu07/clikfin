package com.clikfin.clikfinapplication.externalRequests.Request;

import com.google.gson.annotations.SerializedName;

public class DocumentUrlGenerateRequest{

	@SerializedName("password")
	private String password;

	@SerializedName("document_extension")
	private String documentExtension;

	@SerializedName("document_type_id")
	private int documentTypeId;

	@SerializedName("customer_id")
	private int customerId;

	@SerializedName("loan_id")
	private int loanId;

	public String getPassword(){
		return password;
	}

	public String getDocumentExtension(){
		return documentExtension;
	}

	public int getDocumentTypeId(){
		return documentTypeId;
	}

	public int getCustomerId(){
		return customerId;
	}

	public int getLoanId(){
		return loanId;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setDocumentExtension(String documentExtension) {
		this.documentExtension = documentExtension;
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
}