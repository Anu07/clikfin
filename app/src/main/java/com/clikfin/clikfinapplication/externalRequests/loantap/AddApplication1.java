package com.clikfin.clikfinapplication.externalRequests.loantap;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class AddApplication1 implements Parcelable {

	@SerializedName("job_type")
	private String jobType;

	@SerializedName("employment_year")
	private String employmentYear;

	@SerializedName("req_processing_fees")
	private String reqProcessingFees;

	@SerializedName("gender")
	private String gender;

	@SerializedName("fixed_income")
	private String fixedIncome;

	@SerializedName("home_addr_line2")
	private String homeAddrLine2;

	@SerializedName("office_addr_line1")
	private String officeAddrLine1;

	@SerializedName("loan_city")
	private String loanCity;

	@SerializedName("office_addr_line2")
	private String officeAddrLine2;

	@SerializedName("home_zipcode")
	private String homeZipcode;

	@SerializedName("employer_name")
	private String employerName;

	@SerializedName("permanent_city")
	private String permanentCity;

	@SerializedName("emi_outflow")
	private String emiOutflow;

	@SerializedName("req_interest_rate")
	private String reqInterestRate;

	@SerializedName("req_tenure")
	private String reqTenure;

	@SerializedName("salary_account_no")
	private String salaryAccountNo;

	@SerializedName("home_addr_line1")
	private String homeAddrLine1;

	@SerializedName("office_city")
	private String officeCity;

	@SerializedName("req_amount")
	private String reqAmount;

	@SerializedName("office_zipcode")
	private String officeZipcode;

	@SerializedName("is_consent")
	private String isConsent;

	@SerializedName("permanent_zipcode")
	private String permanentZipcode;

	@SerializedName("pan_card")
	private String panCard;

	@SerializedName("home_city")
	private String homeCity;

	@SerializedName("marital_status")
	private String maritalStatus;

	@SerializedName("permanent_addr_line1")
	private String permanentAddrLine1;

	@SerializedName("rent_outflow")
	private String rentOutflow;

	@SerializedName("full_name")
	private String fullName;

	@SerializedName("address_landmark")
	private String addressLandmark;

	@SerializedName("permanent_addr_line2")
	private String permanentAddrLine2;

	@SerializedName("salary_bank_name")
	private String salaryBankName;

	@SerializedName("dob")
	private String dob;

	@SerializedName("official_email")
	private String officialEmail;

	@SerializedName("years_at_current_residence")
	private String yearsAtCurrentResidence;

	@SerializedName("home_ownership_type")
	private String homeOwnershipType;

	@SerializedName("office_landline_no")
	private String officeLandlineNo;

	@SerializedName("educational_qualification")
	private String educationalQualification;

	@SerializedName("mothers_maiden_name")
	private String mothersMaidenName;

	@SerializedName("mobile_number")
	private String mobileNumber;

	@SerializedName("personal_email")
	private String personalEmail;

	@SerializedName("fathers_name")
	private String fathersName;

	public String getJobType(){
		return jobType;
	}

	public String getEmploymentYear(){
		return employmentYear;
	}

	public String getReqProcessingFees(){
		return reqProcessingFees;
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

	public String getOfficeAddrLine1(){
		return officeAddrLine1;
	}

	public String getLoanCity(){
		return loanCity;
	}

	public String getOfficeAddrLine2(){
		return officeAddrLine2;
	}

	public String getHomeZipcode(){
		return homeZipcode;
	}

	public String getEmployerName(){
		return employerName;
	}

	public String getPermanentCity(){
		return permanentCity;
	}

	public String getEmiOutflow(){
		return emiOutflow;
	}

	public String getReqInterestRate(){
		return reqInterestRate;
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

	public String getOfficeCity(){
		return officeCity;
	}

	public String getReqAmount(){
		return reqAmount;
	}

	public String getOfficeZipcode(){
		return officeZipcode;
	}

	public String getIsConsent(){
		return isConsent;
	}

	public String getPermanentZipcode(){
		return permanentZipcode;
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

	public String getPermanentAddrLine1(){
		return permanentAddrLine1;
	}

	public String getRentOutflow(){
		return rentOutflow;
	}

	public String getFullName(){
		return fullName;
	}

	public String getAddressLandmark(){
		return addressLandmark;
	}

	public String getPermanentAddrLine2(){
		return permanentAddrLine2;
	}

	public String getSalaryBankName(){
		return salaryBankName;
	}

	public String getDob(){
		return dob;
	}

	public String getOfficialEmail(){
		return officialEmail;
	}

	public String getYearsAtCurrentResidence(){
		return yearsAtCurrentResidence;
	}

	public String getHomeOwnershipType(){
		return homeOwnershipType;
	}

	public String getOfficeLandlineNo(){
		return officeLandlineNo;
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

	public String getPersonalEmail(){
		return personalEmail;
	}

	public String getFathersName(){
		return fathersName;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.jobType);
		dest.writeString(this.employmentYear);
		dest.writeString(this.reqProcessingFees);
		dest.writeString(this.gender);
		dest.writeString(this.fixedIncome);
		dest.writeString(this.homeAddrLine2);
		dest.writeString(this.officeAddrLine1);
		dest.writeString(this.loanCity);
		dest.writeString(this.officeAddrLine2);
		dest.writeString(this.homeZipcode);
		dest.writeString(this.employerName);
		dest.writeString(this.permanentCity);
		dest.writeString(this.emiOutflow);
		dest.writeString(this.reqInterestRate);
		dest.writeString(this.reqTenure);
		dest.writeString(this.salaryAccountNo);
		dest.writeString(this.homeAddrLine1);
		dest.writeString(this.officeCity);
		dest.writeString(this.reqAmount);
		dest.writeString(this.officeZipcode);
		dest.writeString(this.isConsent);
		dest.writeString(this.permanentZipcode);
		dest.writeString(this.panCard);
		dest.writeString(this.homeCity);
		dest.writeString(this.maritalStatus);
		dest.writeString(this.permanentAddrLine1);
		dest.writeString(this.rentOutflow);
		dest.writeString(this.fullName);
		dest.writeString(this.addressLandmark);
		dest.writeString(this.permanentAddrLine2);
		dest.writeString(this.salaryBankName);
		dest.writeString(this.dob);
		dest.writeString(this.officialEmail);
		dest.writeString(this.yearsAtCurrentResidence);
		dest.writeString(this.homeOwnershipType);
		dest.writeString(this.officeLandlineNo);
		dest.writeString(this.educationalQualification);
		dest.writeString(this.mothersMaidenName);
		dest.writeString(this.mobileNumber);
		dest.writeString(this.personalEmail);
		dest.writeString(this.fathersName);
	}

	public void readFromParcel(Parcel source) {
		this.jobType = source.readString();
		this.employmentYear = source.readString();
		this.reqProcessingFees = source.readString();
		this.gender = source.readString();
		this.fixedIncome = source.readString();
		this.homeAddrLine2 = source.readString();
		this.officeAddrLine1 = source.readString();
		this.loanCity = source.readString();
		this.officeAddrLine2 = source.readString();
		this.homeZipcode = source.readString();
		this.employerName = source.readString();
		this.permanentCity = source.readString();
		this.emiOutflow = source.readString();
		this.reqInterestRate = source.readString();
		this.reqTenure = source.readString();
		this.salaryAccountNo = source.readString();
		this.homeAddrLine1 = source.readString();
		this.officeCity = source.readString();
		this.reqAmount = source.readString();
		this.officeZipcode = source.readString();
		this.isConsent = source.readString();
		this.permanentZipcode = source.readString();
		this.panCard = source.readString();
		this.homeCity = source.readString();
		this.maritalStatus = source.readString();
		this.permanentAddrLine1 = source.readString();
		this.rentOutflow = source.readString();
		this.fullName = source.readString();
		this.addressLandmark = source.readString();
		this.permanentAddrLine2 = source.readString();
		this.salaryBankName = source.readString();
		this.dob = source.readString();
		this.officialEmail = source.readString();
		this.yearsAtCurrentResidence = source.readString();
		this.homeOwnershipType = source.readString();
		this.officeLandlineNo = source.readString();
		this.educationalQualification = source.readString();
		this.mothersMaidenName = source.readString();
		this.mobileNumber = source.readString();
		this.personalEmail = source.readString();
		this.fathersName = source.readString();
	}

	public AddApplication1() {
	}

	protected AddApplication1(Parcel in) {
		this.jobType = in.readString();
		this.employmentYear = in.readString();
		this.reqProcessingFees = in.readString();
		this.gender = in.readString();
		this.fixedIncome = in.readString();
		this.homeAddrLine2 = in.readString();
		this.officeAddrLine1 = in.readString();
		this.loanCity = in.readString();
		this.officeAddrLine2 = in.readString();
		this.homeZipcode = in.readString();
		this.employerName = in.readString();
		this.permanentCity = in.readString();
		this.emiOutflow = in.readString();
		this.reqInterestRate = in.readString();
		this.reqTenure = in.readString();
		this.salaryAccountNo = in.readString();
		this.homeAddrLine1 = in.readString();
		this.officeCity = in.readString();
		this.reqAmount = in.readString();
		this.officeZipcode = in.readString();
		this.isConsent = in.readString();
		this.permanentZipcode = in.readString();
		this.panCard = in.readString();
		this.homeCity = in.readString();
		this.maritalStatus = in.readString();
		this.permanentAddrLine1 = in.readString();
		this.rentOutflow = in.readString();
		this.fullName = in.readString();
		this.addressLandmark = in.readString();
		this.permanentAddrLine2 = in.readString();
		this.salaryBankName = in.readString();
		this.dob = in.readString();
		this.officialEmail = in.readString();
		this.yearsAtCurrentResidence = in.readString();
		this.homeOwnershipType = in.readString();
		this.officeLandlineNo = in.readString();
		this.educationalQualification = in.readString();
		this.mothersMaidenName = in.readString();
		this.mobileNumber = in.readString();
		this.personalEmail = in.readString();
		this.fathersName = in.readString();
	}

	public static final Parcelable.Creator<AddApplication1> CREATOR = new Parcelable.Creator<AddApplication1>() {
		@Override
		public AddApplication1 createFromParcel(Parcel source) {
			return new AddApplication1(source);
		}

		@Override
		public AddApplication1[] newArray(int size) {
			return new AddApplication1[size];
		}
	};

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public void setEmploymentYear(String employmentYear) {
		this.employmentYear = employmentYear;
	}

	public void setReqProcessingFees(String reqProcessingFees) {
		this.reqProcessingFees = reqProcessingFees;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setFixedIncome(String fixedIncome) {
		this.fixedIncome = fixedIncome;
	}

	public void setHomeAddrLine2(String homeAddrLine2) {
		this.homeAddrLine2 = homeAddrLine2;
	}

	public void setOfficeAddrLine1(String officeAddrLine1) {
		this.officeAddrLine1 = officeAddrLine1;
	}

	public void setLoanCity(String loanCity) {
		this.loanCity = loanCity;
	}

	public void setOfficeAddrLine2(String officeAddrLine2) {
		this.officeAddrLine2 = officeAddrLine2;
	}

	public void setHomeZipcode(String homeZipcode) {
		this.homeZipcode = homeZipcode;
	}

	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}

	public void setPermanentCity(String permanentCity) {
		this.permanentCity = permanentCity;
	}

	public void setEmiOutflow(String emiOutflow) {
		this.emiOutflow = emiOutflow;
	}

	public void setReqInterestRate(String reqInterestRate) {
		this.reqInterestRate = reqInterestRate;
	}

	public void setReqTenure(String reqTenure) {
		this.reqTenure = reqTenure;
	}

	public void setSalaryAccountNo(String salaryAccountNo) {
		this.salaryAccountNo = salaryAccountNo;
	}

	public void setHomeAddrLine1(String homeAddrLine1) {
		this.homeAddrLine1 = homeAddrLine1;
	}

	public void setOfficeCity(String officeCity) {
		this.officeCity = officeCity;
	}

	public void setReqAmount(String reqAmount) {
		this.reqAmount = reqAmount;
	}

	public void setOfficeZipcode(String officeZipcode) {
		this.officeZipcode = officeZipcode;
	}

	public void setIsConsent(String isConsent) {
		this.isConsent = isConsent;
	}

	public void setPermanentZipcode(String permanentZipcode) {
		this.permanentZipcode = permanentZipcode;
	}

	public void setPanCard(String panCard) {
		this.panCard = panCard;
	}

	public void setHomeCity(String homeCity) {
		this.homeCity = homeCity;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public void setPermanentAddrLine1(String permanentAddrLine1) {
		this.permanentAddrLine1 = permanentAddrLine1;
	}

	public void setRentOutflow(String rentOutflow) {
		this.rentOutflow = rentOutflow;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setAddressLandmark(String addressLandmark) {
		this.addressLandmark = addressLandmark;
	}

	public void setPermanentAddrLine2(String permanentAddrLine2) {
		this.permanentAddrLine2 = permanentAddrLine2;
	}

	public void setSalaryBankName(String salaryBankName) {
		this.salaryBankName = salaryBankName;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public void setOfficialEmail(String officialEmail) {
		this.officialEmail = officialEmail;
	}

	public void setYearsAtCurrentResidence(String yearsAtCurrentResidence) {
		this.yearsAtCurrentResidence = yearsAtCurrentResidence;
	}

	public void setHomeOwnershipType(String homeOwnershipType) {
		this.homeOwnershipType = homeOwnershipType;
	}

	public void setOfficeLandlineNo(String officeLandlineNo) {
		this.officeLandlineNo = officeLandlineNo;
	}

	public void setEducationalQualification(String educationalQualification) {
		this.educationalQualification = educationalQualification;
	}

	public void setMothersMaidenName(String mothersMaidenName) {
		this.mothersMaidenName = mothersMaidenName;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public void setPersonalEmail(String personalEmail) {
		this.personalEmail = personalEmail;
	}

	public void setFathersName(String fathersName) {
		this.fathersName = fathersName;
	}
}