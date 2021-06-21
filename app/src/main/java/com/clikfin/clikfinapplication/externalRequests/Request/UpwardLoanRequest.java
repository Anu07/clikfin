package com.clikfin.clikfinapplication.externalRequests.Request;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class UpwardLoanRequest{


	@NonNull
	@SerializedName("bank_account_holder_full_name")
	private String bankAccountHolderFullName;

	@NonNull
	@SerializedName("bank_branch")
	private String bankBranch;

	@NonNull
	@SerializedName("salary")
	private int salary;
	@NonNull
	@SerializedName("current_company_joining_date")
	private String currentCompanyJoiningDate;

	@NonNull
	@SerializedName("bank account number")
	private String bankAccountNumber;
	@NonNull
	@SerializedName("current_pincode")
	private String currentPincode;
	@NonNull
	@SerializedName("social_email_id")
	private String socialEmailId;
	@NonNull
	@SerializedName("pan")
	private String pan;
	@NonNull
	@SerializedName("ifsc")
	private String ifsc;
	@NonNull
	@SerializedName("mother_first_name")
	private String motherFirstName;
	@NonNull
	@SerializedName("highest_education_institute_name")
	private String highestEducationInstituteName;
	@NonNull
	@SerializedName("current_address_line2")
	private String currentAddressLine2;
	@NonNull
	@SerializedName("current_employment_tenure_category_id")
	private int currentEmploymentTenureCategoryId;
	@NonNull
	@SerializedName("current_address_line1")
	private String currentAddressLine1;
	@NonNull
	@SerializedName("mobile_number1")
	private String mobileNumber1;
	@NonNull
	@SerializedName("current_company_state")
	private String currentCompanyState;

	@NonNull
	@SerializedName("dob")
	private String dob;
	@NonNull
	@SerializedName("marital_status_id")
	private int maritalStatusId;
	@NonNull
	@SerializedName("work_email_id")
	private String workEmailId;
	@NonNull
	@SerializedName("affiliate_loan_identifier")
	private int affiliateLoanIdentifier;
	@NonNull
	@SerializedName("bank_district")
	private String bankDistrict;
	@NonNull
	@SerializedName("employment_status_id")
	private int employmentStatusId;
	@NonNull
	@SerializedName("mother_last_name")
	private String motherLastName;
	@NonNull
	@SerializedName("is_partial_data")
	private boolean isPartialData;
	@NonNull
	@SerializedName("current_company_pincode")
	private String currentCompanyPincode;
	@NonNull
	@SerializedName("gender")
	private String gender;
	@NonNull
	@SerializedName("current_company_city")
	private String currentCompanyCity;
	@NonNull
	@SerializedName("total_work_experience_category_id")
	private int totalWorkExperienceCategoryId;
	@NonNull
	@SerializedName("organization_type_id")
	private int organizationTypeId;
	@NonNull
	@SerializedName("bank_name")
	private String bankName;
	@NonNull
	@SerializedName("father_last_name")
	private String fatherLastName;
	@NonNull
	@SerializedName("aadhaar")
	private long aadhaar;
	@NonNull
	@SerializedName("company")
	private String company;
	@NonNull
	@SerializedName("salary_payment_mode_id")
	private int salaryPaymentModeId;
	@NonNull
	@SerializedName("first_name")
	private String firstName;
	@NonNull
	@SerializedName("bank_state")
	private String bankState;
	@NonNull
	@SerializedName("bank_code")
	private String bankCode;
	@NonNull
	@SerializedName("loan_purpose_id")
	private int loanPurposeId;
	@NonNull
	@SerializedName("current_company_address_line1")
	private String currentCompanyAddressLine1;
	@NonNull
	@SerializedName("current_residence_type_id")
	private int currentResidenceTypeId;
	@NonNull
	@SerializedName("current_company_address_line2")
	private String currentCompanyAddressLine2;
	@NonNull
	@SerializedName("current_state")
	private String currentState;
	@NonNull
	@SerializedName("last_name")
	private String lastName;
	@NonNull
	@SerializedName("profession_type_id")
	private int professionTypeId;
	@NonNull
	@SerializedName("father_first_name")
	private String fatherFirstName;
	@NonNull
	@SerializedName("current_city")
	private String currentCity;
	@NonNull
	@SerializedName("current_residence_stay_category_id")
	private int currentResidenceStayCategoryId;
	@NonNull
	@SerializedName("qualification_type_id")
	private int qualificationTypeId;
	@NonNull
	@SerializedName("bank_city")
	private String bankCity;

	public String getBankAccountHolderFullName(){
		return bankAccountHolderFullName;
	}

	public String getBankBranch(){
		return bankBranch;
	}

	public int getSalary(){
		return salary;
	}

	public String getCurrentCompanyJoiningDate(){
		return currentCompanyJoiningDate;
	}

	public String getBankAccountNumber(){
		return bankAccountNumber;
	}

	public String getCurrentPincode(){
		return currentPincode;
	}

	public String getSocialEmailId(){
		return socialEmailId;
	}

	public String getPan(){
		return pan;
	}

	public String getIfsc(){
		return ifsc;
	}

	public String getMotherFirstName(){
		return motherFirstName;
	}

	public String getHighestEducationInstituteName(){
		return highestEducationInstituteName;
	}

	public String getCurrentAddressLine2(){
		return currentAddressLine2;
	}

	public int getCurrentEmploymentTenureCategoryId(){
		return currentEmploymentTenureCategoryId;
	}

	public String getCurrentAddressLine1(){
		return currentAddressLine1;
	}

	public String getMobileNumber1(){
		return mobileNumber1;
	}

	public String getCurrentCompanyState(){
		return currentCompanyState;
	}

	public String getDob(){
		return dob;
	}

	public int getMaritalStatusId(){
		return maritalStatusId;
	}

	public String getWorkEmailId(){
		return workEmailId;
	}

	public int getAffiliateLoanIdentifier(){
		return affiliateLoanIdentifier;
	}

	public String getBankDistrict(){
		return bankDistrict;
	}

	public int getEmploymentStatusId(){
		return employmentStatusId;
	}

	public String getMotherLastName(){
		return motherLastName;
	}

	public boolean isIsPartialData(){
		return isPartialData;
	}

	public String getCurrentCompanyPincode(){
		return currentCompanyPincode;
	}

	public String getGender(){
		return gender;
	}

	public String getCurrentCompanyCity(){
		return currentCompanyCity;
	}

	public int getTotalWorkExperienceCategoryId(){
		return totalWorkExperienceCategoryId;
	}

	public int getOrganizationTypeId(){
		return organizationTypeId;
	}

	public String getBankName(){
		return bankName;
	}

	public String getFatherLastName(){
		return fatherLastName;
	}

	public long getAadhaar(){
		return aadhaar;
	}

	public String getCompany(){
		return company;
	}

	public int getSalaryPaymentModeId(){
		return salaryPaymentModeId;
	}

	public String getFirstName(){
		return firstName;
	}

	public String getBankState(){
		return bankState;
	}

	public String getBankCode(){
		return bankCode;
	}

	public int getLoanPurposeId(){
		return loanPurposeId;
	}

	public String getCurrentCompanyAddressLine1(){
		return currentCompanyAddressLine1;
	}

	public int getCurrentResidenceTypeId(){
		return currentResidenceTypeId;
	}

	public String getCurrentCompanyAddressLine2(){
		return currentCompanyAddressLine2;
	}

	public String getCurrentState(){
		return currentState;
	}

	public String getLastName(){
		return lastName;
	}

	public int getProfessionTypeId(){
		return professionTypeId;
	}

	public String getFatherFirstName(){
		return fatherFirstName;
	}

	public String getCurrentCity(){
		return currentCity;
	}

	public int getCurrentResidenceStayCategoryId(){
		return currentResidenceStayCategoryId;
	}

	public int getQualificationTypeId(){
		return qualificationTypeId;
	}

	public String getBankCity(){
		return bankCity;
	}

	public void setBankAccountHolderFullName(String bankAccountHolderFullName) {
		this.bankAccountHolderFullName = bankAccountHolderFullName;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public void setCurrentCompanyJoiningDate(String currentCompanyJoiningDate) {
		this.currentCompanyJoiningDate = currentCompanyJoiningDate;
	}

	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}

	public void setCurrentPincode(String currentPincode) {
		this.currentPincode = currentPincode;
	}

	public void setSocialEmailId(String socialEmailId) {
		this.socialEmailId = socialEmailId;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}

	public void setMotherFirstName(String motherFirstName) {
		this.motherFirstName = motherFirstName;
	}

	public void setHighestEducationInstituteName(String highestEducationInstituteName) {
		this.highestEducationInstituteName = highestEducationInstituteName;
	}

	public void setCurrentAddressLine2(String currentAddressLine2) {
		this.currentAddressLine2 = currentAddressLine2;
	}

	public void setCurrentEmploymentTenureCategoryId(int currentEmploymentTenureCategoryId) {
		this.currentEmploymentTenureCategoryId = currentEmploymentTenureCategoryId;
	}

	public void setCurrentAddressLine1(String currentAddressLine1) {
		this.currentAddressLine1 = currentAddressLine1;
	}

	public void setMobileNumber1(String mobileNumber1) {
		this.mobileNumber1 = mobileNumber1;
	}

	public void setCurrentCompanyState(String currentCompanyState) {
		this.currentCompanyState = currentCompanyState;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public void setMaritalStatusId(int maritalStatusId) {
		this.maritalStatusId = maritalStatusId;
	}

	public void setWorkEmailId(String workEmailId) {
		this.workEmailId = workEmailId;
	}

	public void setAffiliateLoanIdentifier(int affiliateLoanIdentifier) {
		this.affiliateLoanIdentifier = affiliateLoanIdentifier;
	}

	public void setBankDistrict(String bankDistrict) {
		this.bankDistrict = bankDistrict;
	}

	public void setEmploymentStatusId(int employmentStatusId) {
		this.employmentStatusId = employmentStatusId;
	}

	public void setMotherLastName(String motherLastName) {
		this.motherLastName = motherLastName;
	}

	public boolean isPartialData() {
		return isPartialData;
	}

	public void setPartialData(boolean partialData) {
		isPartialData = partialData;
	}

	public void setCurrentCompanyPincode(String currentCompanyPincode) {
		this.currentCompanyPincode = currentCompanyPincode;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setCurrentCompanyCity(String currentCompanyCity) {
		this.currentCompanyCity = currentCompanyCity;
	}

	public void setTotalWorkExperienceCategoryId(int totalWorkExperienceCategoryId) {
		this.totalWorkExperienceCategoryId = totalWorkExperienceCategoryId;
	}

	public void setOrganizationTypeId(int organizationTypeId) {
		this.organizationTypeId = organizationTypeId;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public void setFatherLastName(String fatherLastName) {
		this.fatherLastName = fatherLastName;
	}

	public void setAadhaar(long aadhaar) {
		this.aadhaar = aadhaar;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public void setSalaryPaymentModeId(int salaryPaymentModeId) {
		this.salaryPaymentModeId = salaryPaymentModeId;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setBankState(String bankState) {
		this.bankState = bankState;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public void setLoanPurposeId(int loanPurposeId) {
		this.loanPurposeId = loanPurposeId;
	}

	public void setCurrentCompanyAddressLine1(String currentCompanyAddressLine1) {
		this.currentCompanyAddressLine1 = currentCompanyAddressLine1;
	}

	public void setCurrentResidenceTypeId(int currentResidenceTypeId) {
		this.currentResidenceTypeId = currentResidenceTypeId;
	}

	public void setCurrentCompanyAddressLine2(String currentCompanyAddressLine2) {
		this.currentCompanyAddressLine2 = currentCompanyAddressLine2;
	}

	public void setCurrentState(String currentState) {
		this.currentState = currentState;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setProfessionTypeId(int professionTypeId) {
		this.professionTypeId = professionTypeId;
	}

	public void setFatherFirstName(String fatherFirstName) {
		this.fatherFirstName = fatherFirstName;
	}

	public void setCurrentCity(String currentCity) {
		this.currentCity = currentCity;
	}

	public void setCurrentResidenceStayCategoryId(int currentResidenceStayCategoryId) {
		this.currentResidenceStayCategoryId = currentResidenceStayCategoryId;
	}

	public void setQualificationTypeId(int qualificationTypeId) {
		this.qualificationTypeId = qualificationTypeId;
	}

	public void setBankCity(String bankCity) {
		this.bankCity = bankCity;
	}
}