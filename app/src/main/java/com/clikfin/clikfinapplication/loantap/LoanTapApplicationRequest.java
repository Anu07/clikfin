package com.clikfin.clikfinapplication.loantap;

import com.google.gson.annotations.SerializedName;

public class LoanTapApplicationRequest{

	@SerializedName("add_application+1")
	private AddApplication1 addApplication1;

	public AddApplication1 getAddApplication1(){
		return addApplication1;
	}
}