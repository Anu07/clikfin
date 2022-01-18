package com.clikfin.clikfinapplication.externalRequests.Response.loantapResponse.upload;

import com.google.gson.annotations.SerializedName;

public class Error{

	@SerializedName("status_code")
	private String statusCode;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private String status;

	public String getStatusCode(){
		return statusCode;
	}

	public String getMessage(){
		return message;
	}

	public String getStatus(){
		return status;
	}
}