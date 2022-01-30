package com.clikfin.clikfinapplication.externalRequests.Response.loantapResponse.addApplication;

import com.google.gson.annotations.SerializedName;

public class AddApplicationResponse{

	@SerializedName("add_application+1")
	private AddApplication1 addApplication1;

	public AddApplication1 getAddApplication1(){
		return addApplication1;
	}
}