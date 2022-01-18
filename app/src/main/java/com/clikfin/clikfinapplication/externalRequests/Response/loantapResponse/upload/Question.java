package com.clikfin.clikfinapplication.externalRequests.Response.loantapResponse.upload;

import com.google.gson.annotations.SerializedName;

public class Question{

	@SerializedName("ext")
	private String ext;

	@SerializedName("file")
	private String file;

	@SerializedName("file_name")
	private String fileName;

	@SerializedName("application_id")
	private String applicationId;

	public String getExt(){
		return ext;
	}

	public String getFile(){
		return file;
	}

	public String getFileName(){
		return fileName;
	}

	public String getApplicationId(){
		return applicationId;
	}
}