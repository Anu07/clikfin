package com.clikfin.clikfinapplication.externalRequests.Response.loantapResponse.addApplication;

import com.google.gson.annotations.SerializedName;

public class Errors{

	@SerializedName("personal_email")
	private String personalEmail;

	public String getPersonalEmail(){
		return personalEmail;
	}
}