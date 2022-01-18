package com.clikfin.clikfinapplication.externalRequests.Response.loantapResponse.upload;

import com.google.gson.annotations.SerializedName;

public class UploadDocument1{

	@SerializedName("question")
	private Question question;

	@SerializedName("error")
	private Error error;

	public Question getQuestion(){
		return question;
	}

	public Error getError(){
		return error;
	}
}