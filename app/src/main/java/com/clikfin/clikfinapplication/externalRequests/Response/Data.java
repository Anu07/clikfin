package com.clikfin.clikfinapplication.externalRequests.Response;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("loan_status")
	private String loanStatus;

	@SerializedName("loan_data")
	private LoanData loanData;

	public String getLoanStatus(){
		return loanStatus;
	}

	public LoanData getLoanData(){
		return loanData;
	}
}