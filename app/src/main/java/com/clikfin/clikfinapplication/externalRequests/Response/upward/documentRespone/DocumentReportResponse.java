package com.clikfin.clikfinapplication.externalRequests.Response.upward.documentRespone;

import com.google.gson.annotations.SerializedName;

public class DocumentReportResponse{

	@SerializedName("data")
	private Data data;

	@SerializedName("meta")
	private String meta;

	public Data getData(){
		return data;
	}

	public String getMeta(){
		return meta;
	}
}