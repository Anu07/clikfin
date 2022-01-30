package com.clikfin.clikfinapplication.externalRequests.Response.loantapResponse.addApplication;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("lapp_id")
	private String lappId;

	@SerializedName("link")
	private String link;

	public String getLappId(){
		return lappId;
	}

	public String getLink(){
		return link;
	}
}