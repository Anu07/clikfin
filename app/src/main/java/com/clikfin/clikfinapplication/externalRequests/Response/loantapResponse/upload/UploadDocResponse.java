package com.clikfin.clikfinapplication.externalRequests.Response.loantapResponse.upload;

import com.google.gson.annotations.SerializedName;

public class UploadDocResponse{

	@SerializedName("upload_document+1")
	private UploadDocument1 uploadDocument1;

	public UploadDocument1 getUploadDocument1(){
		return uploadDocument1;
	}
}