package com.clikfin.clikfinapplication.externalRequests.Response;

import com.google.gson.annotations.SerializedName;

public class LoanStatusResponse{

	@SerializedName("data")
	private Data data;

	@SerializedName("meta")
	private String meta;

	public Data getData(){
		return data;
	}

	public String getMeta(){
		return meta;
	}
}