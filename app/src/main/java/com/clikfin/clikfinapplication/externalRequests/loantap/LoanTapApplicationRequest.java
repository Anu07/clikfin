package com.clikfin.clikfinapplication.externalRequests.loantap;

import com.clikfin.clikfinapplication.externalRequests.loantap.enquireRequest.AddApplication;
import com.google.gson.annotations.SerializedName;

public class LoanTapApplicationRequest{

	@SerializedName("add_application+1")
	private AddApplication addApplication1;

	public AddApplication getAddApplication1(){
		return addApplication1;
	}
}