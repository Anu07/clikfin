package com.clikfin.clikfinapplication.externalRequests.Response;

import com.google.gson.annotations.SerializedName;

public class LoanData{

	@SerializedName("emi")
	private int emi;

	@SerializedName("amount")
	private int amount;

	@SerializedName("interest_rate")
	private double interestRate;

	@SerializedName("tenure")
	private int tenure;

	public int getEmi(){
		return emi;
	}

	public int getAmount(){
		return amount;
	}

	public double getInterestRate(){
		return interestRate;
	}

	public int getTenure(){
		return tenure;
	}
}