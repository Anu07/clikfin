package com.clikfin.clikfinapplication.externalRequests.Response.loantapResponse.addApplication;

import com.google.gson.annotations.SerializedName;

public class Question{

	@SerializedName("job_type")
	private String jobType;

	@SerializedName("employment_year")
	private String employmentYear;

	@SerializedName("gender")
	private String gender;

	@SerializedName("fixed_income")
	private String fixedIncome;

	@SerializedName("home_addr_line2")
	private String homeAddrLine2;

	@SerializedName("loan_city")
	private String loanCity;

	@SerializedName("home_zipcode")
	private String homeZipcode;

	@SerializedName("employer_name")
	private String employerName;

	@SerializedName("emi_outflow")
	private String emiOutflow;

	@SerializedName("req_tenure")
	private String reqTenure;

	@SerializedName("salary_account_no")
	private String salaryAccountNo;

	@SerializedName("home_addr_line1")
	private String homeAddrLine1;

	@SerializedName("req_amount")
	private String reqAmount;

	@SerializedName("pan_card")
	private String panCard;

	@SerializedName("home_city")
	private String homeCity;

	@SerializedName("marital_status")
	private String maritalStatus;

	@SerializedName("address_landmark")
	private String addressLandmark;

	@SerializedName("full_name")
	private String fullName;

	@SerializedName("salary_bank_name")
	private String salaryBankName;

	@SerializedName("dob")
	private String dob;

	@SerializedName("home_ownership_type")
	private String homeOwnershipType;

	@SerializedName("educational_qualification")
	private String educationalQualification;

	@SerializedName("mothers_maiden_name")
	private String mothersMaidenName;

	@SerializedName("mobile_number")
	private String mobileNumber;

	@SerializedName("fathers_name")
	private String fathersName;

	public String getJobType(){
		return jobType;
	}

	public String getEmploymentYear(){
		return employmentYear;
	}

	public String getGender(){
		return gender;
	}

	public String getFixedIncome(){
		return fixedIncome;
	}

	public String getHomeAddrLine2(){
		return homeAddrLine2;
	}

	public String getLoanCity(){
		return loanCity;
	}

	public String getHomeZipcode(){
		return homeZipcode;
	}

	public String getEmployerName(){
		return employerName;
	}

	public String getEmiOutflow(){
		return emiOutflow;
	}

	public String getReqTenure(){
		return reqTenure;
	}

	public String getSalaryAccountNo(){
		return salaryAccountNo;
	}

	public String getHomeAddrLine1(){
		return homeAddrLine1;
	}

	public String getReqAmount(){
		return reqAmount;
	}

	public String getPanCard(){
		return panCard;
	}

	public String getHomeCity(){
		return homeCity;
	}

	public String getMaritalStatus(){
		return maritalStatus;
	}

	public String getAddressLandmark(){
		return addressLandmark;
	}

	public String getFullName(){
		return fullName;
	}

	public String getSalaryBankName(){
		return salaryBankName;
	}

	public String getDob(){
		return dob;
	}

	public String getHomeOwnershipType(){
		return homeOwnershipType;
	}

	public String getEducationalQualification(){
		return educationalQualification;
	}

	public String getMothersMaidenName(){
		return mothersMaidenName;
	}

	public String getMobileNumber(){
		return mobileNumber;
	}

	public String getFathersName(){
		return fathersName;
	}
}