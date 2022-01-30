package com.clikfin.clikfinapplication.externalRequests.Response.loantapResponse.addApplication.proofLoanTap;

import com.google.gson.annotations.SerializedName;

public class ProofJsonItem{

	@SerializedName("label")
	private String label;

	@SerializedName("value")
	private String value;

	public String getLabel(){
		return label;
	}

	public String getValue(){
		return value;
	}
}