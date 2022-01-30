package com.clikfin.clikfinapplication.externalRequests.Response.loantapResponse.addApplication;

import com.google.gson.annotations.SerializedName;

public class AddApplication1{

	@SerializedName("question")
	private Question question;

	@SerializedName("error")
	private Error error;

	@SerializedName("answer")
	private Answer answer;

	public Question getQuestion(){
		return question;
	}

	public Error getError(){
		return error;
	}

	public Answer getAnswer() {
		return answer;
	}
}