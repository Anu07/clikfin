package com.clikfin.clikfinapplication.externalRequests.Response.loantapResponse.addApplication;

import com.google.gson.annotations.SerializedName;

public class Answer{

	@SerializedName("status_code")
	private String statusCode;

	@SerializedName("data")
	private Data data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private String status;

	public String getStatusCode(){
		return statusCode;
	}

	public Data getData(){
		return data;
	}

	public String getMessage(){
		return message;
	}

	public String getStatus(){
		return status;
	}
}