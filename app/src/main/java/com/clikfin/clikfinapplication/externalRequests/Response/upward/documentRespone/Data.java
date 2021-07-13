package com.clikfin.clikfinapplication.externalRequests.Response.upward.documentRespone;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("document_data")
	private DocumentData documentData;

	public DocumentData getDocumentData(){
		return documentData;
	}
}