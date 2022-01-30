package com.clikfin.clikfinapplication.externalRequests.Response.loantapResponse.addApplication;

import com.google.gson.annotations.SerializedName;

public class Error{

	@SerializedName("status_code")
	private String statusCode;

	@SerializedName("message")
	private String message;

	@SerializedName("errors")
	private Errors errors;

	@SerializedName("status")
	private String status;

	public String getStatusCode(){
		return statusCode;
	}

	public String getMessage(){
		return message;
	}

	public Errors getErrors(){
		return errors;
	}

	public String getStatus(){
		return status;
	}
}