package com.clikfin.clikfinapplication.externalRequests.loantap.enquireRequest;

import com.google.gson.annotations.SerializedName;

public class AddApplicationRequest{

	@SerializedName("add_application+1")
	private AddApplication addApplication1;

	public AddApplication getAddApplication1(){
		return addApplication1;
	}

	public void setAddApplication1(AddApplication addApplication1) {
		this.addApplication1 = addApplication1;
	}
}