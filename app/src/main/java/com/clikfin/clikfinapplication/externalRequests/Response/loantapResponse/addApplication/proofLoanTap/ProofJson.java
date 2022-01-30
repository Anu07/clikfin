package com.clikfin.clikfinapplication.externalRequests.Response.loantapResponse.addApplication.proofLoanTap;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ProofJson{

	@SerializedName("ProofJson")
	private List<ProofJsonItem> proofJson;

	public List<ProofJsonItem> getProofJson(){
		return proofJson;
	}
}