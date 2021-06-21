package com.clikfin.clikfinapplication.externalRequests.Request;

import com.google.gson.annotations.SerializedName;

public class ApplicationStatusRequest{

	@SerializedName("level_id")
	private int levelId;

	@SerializedName("customer_id")
	private int customerId;

	@SerializedName("loan_id")
	private int loanId;

	public ApplicationStatusRequest(int level, int customerId, int loanId) {
		this.levelId=level;
		this.customerId= customerId;
		this.loanId = loanId;
	}

	public int getLevelId(){
		return levelId;
	}

	public int getCustomerId(){
		return customerId;
	}

	public int getLoanId(){
		return loanId;
	}
}