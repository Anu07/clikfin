package com.clikfin.clikfinapplication.externalRequests.Request;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PersonalDetails implements Parcelable {
    @SerializedName("firstName")
    String firstName;
    @SerializedName("lastName")
    String lastName;
    @SerializedName("middleName")
    String middleName;
    @SerializedName("motherName")
    String motherName;
    @SerializedName("spouseName")
    String spouseName;
    @SerializedName("fatherName")
    String fatherName;
    @SerializedName("educationalQualification")
    String educationalQualification;

    public String getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
    }

    String formattedDate="";
    public String getEducationalQualification() {
        return educationalQualification;
    }

    public void setEducationalQualification(String educationalQualification) {
        this.educationalQualification = educationalQualification;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getSpouseName() {
        return spouseName;
    }

    public void setSpouseName(String spouseName) {
        this.spouseName = spouseName;
    }

    @SerializedName("phoneNumber")
    String phoneNumber;
    @SerializedName("email")
    String email;
    @SerializedName("aadhaarNumber")
    String aadhaarNumber;
    @SerializedName("panNumber")
    String panNumber;
    @SerializedName("maritalStatus")
    String maritalStatus;
    @SerializedName("gender")
    String gender;

    public String getPermanentAddressStayingFor() {
        return permanentAddressStayingFor;
    }

    public void setPermanentAddressStayingFor(String permanentAddressStayingFor) {
        this.permanentAddressStayingFor = permanentAddressStayingFor;
    }

    public String getPermanentAddressType() {
        return permanentAddressType;
    }

    public void setPermanentAddressType(String permanentAddressType) {
        this.permanentAddressType = permanentAddressType;
    }

    @SerializedName("permanentAddressStayingFor")
    String permanentAddressStayingFor;
    @SerializedName("permanentAddressType")
    String permanentAddressType;
    @SerializedName("dateOfBirth")
    String dateOfBirth;
    @SerializedName("currentAddressLine1")
    String currentAddressLine1;
    @SerializedName("currentAddressLine2")
    String currentAddressLine2;
    @SerializedName("currentAddressLandmark")
    String currentAddressLandmark;
    @SerializedName("currentAddressState")
    String currentAddressState;
    @SerializedName("currentAddressCity")
    String currentAddressCity;
    @SerializedName("currentAddressPinCode")
    String currentAddressPinCode;
    @SerializedName("permanentAddressLine1")
    String permanentAddressLine1;
    @SerializedName("permanentAddressLine2")
    String permanentAddressLine2;
    @SerializedName("permanentAddressLandmark")
    String permanentAddressLandmark;
    @SerializedName("permanentAddressState")
    String permanentAddressState;
    @SerializedName("permanentAddressCity")
    String permanentAddressCity;
    @SerializedName("permanentAddressPinCode")
    String permanentAddressPinCode;
    @SerializedName("addressProof")
    String addressProof;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAadhaarNumber() {
        return aadhaarNumber;
    }

    public void setAadhaarNumber(String aadhaarNumber) {
        this.aadhaarNumber = aadhaarNumber;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCurrentAddressLine1() {
        return currentAddressLine1;
    }

    public void setCurrentAddressLine1(String currentAddressLine1) {
        this.currentAddressLine1 = currentAddressLine1;
    }

    public String getCurrentAddressLine2() {
        return currentAddressLine2;
    }

    public void setCurrentAddressLine2(String currentAddressLine2) {
        this.currentAddressLine2 = currentAddressLine2;
    }

    public String getCurrentAddressLandmark() {
        return currentAddressLandmark;
    }

    public void setCurrentAddressLandmark(String currentAddressLandmark) {
        this.currentAddressLandmark = currentAddressLandmark;
    }

    public String getCurrentAddressState() {
        return currentAddressState;
    }

    public void setCurrentAddressState(String currentAddressState) {
        this.currentAddressState = currentAddressState;
    }

    public String getCurrentAddressCity() {
        return currentAddressCity;
    }

    public void setCurrentAddressCity(String currentAddressCity) {
        this.currentAddressCity = currentAddressCity;
    }

    public String getCurrentAddressPinCode() {
        return currentAddressPinCode;
    }

    public void setCurrentAddressPinCode(String currentAddressPinCode) {
        this.currentAddressPinCode = currentAddressPinCode;
    }

    public String getPermanentAddressLine1() {
        return permanentAddressLine1;
    }

    public void setPermanentAddressLine1(String permanentAddressLine1) {
        this.permanentAddressLine1 = permanentAddressLine1;
    }

    public String getPermanentAddressLine2() {
        return permanentAddressLine2;
    }

    public void setPermanentAddressLine2(String permanentAddressLine2) {
        this.permanentAddressLine2 = permanentAddressLine2;
    }

    public String getPermanentAddressLandmark() {
        return permanentAddressLandmark;
    }

    public void setPermanentAddressLandmark(String permanentAddressLandmark) {
        this.permanentAddressLandmark = permanentAddressLandmark;
    }

    public String getPermanentAddressState() {
        return permanentAddressState;
    }

    public void setPermanentAddressState(String permanentAddressState) {
        this.permanentAddressState = permanentAddressState;
    }

    public String getPermanentAddressCity() {
        return permanentAddressCity;
    }

    public void setPermanentAddressCity(String permanentAddressCity) {
        this.permanentAddressCity = permanentAddressCity;
    }

    public String getPermanentAddressPinCode() {
        return permanentAddressPinCode;
    }

    public void setPermanentAddressPinCode(String permanentAddressPinCode) {
        this.permanentAddressPinCode = permanentAddressPinCode;
    }

    public String getAddressProof() {
        return addressProof;
    }

    public void setAddressProof(String addressProof) {
        this.addressProof = addressProof;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.middleName);
        dest.writeString(this.motherName);
        dest.writeString(this.spouseName);
        dest.writeString(this.fatherName);
        dest.writeString(this.educationalQualification);
        dest.writeString(this.formattedDate);
        dest.writeString(this.phoneNumber);
        dest.writeString(this.email);
        dest.writeString(this.aadhaarNumber);
        dest.writeString(this.panNumber);
        dest.writeString(this.maritalStatus);
        dest.writeString(this.gender);
        dest.writeString(this.permanentAddressStayingFor);
        dest.writeString(this.permanentAddressType);
        dest.writeString(this.dateOfBirth);
        dest.writeString(this.currentAddressLine1);
        dest.writeString(this.currentAddressLine2);
        dest.writeString(this.currentAddressLandmark);
        dest.writeString(this.currentAddressState);
        dest.writeString(this.currentAddressCity);
        dest.writeString(this.currentAddressPinCode);
        dest.writeString(this.permanentAddressLine1);
        dest.writeString(this.permanentAddressLine2);
        dest.writeString(this.permanentAddressLandmark);
        dest.writeString(this.permanentAddressState);
        dest.writeString(this.permanentAddressCity);
        dest.writeString(this.permanentAddressPinCode);
        dest.writeString(this.addressProof);
    }

    public void readFromParcel(Parcel source) {
        this.firstName = source.readString();
        this.lastName = source.readString();
        this.middleName = source.readString();
        this.motherName = source.readString();
        this.spouseName = source.readString();
        this.fatherName = source.readString();
        this.educationalQualification = source.readString();
        this.formattedDate = source.readString();
        this.phoneNumber = source.readString();
        this.email = source.readString();
        this.aadhaarNumber = source.readString();
        this.panNumber = source.readString();
        this.maritalStatus = source.readString();
        this.gender = source.readString();
        this.permanentAddressStayingFor = source.readString();
        this.permanentAddressType = source.readString();
        this.dateOfBirth = source.readString();
        this.currentAddressLine1 = source.readString();
        this.currentAddressLine2 = source.readString();
        this.currentAddressLandmark = source.readString();
        this.currentAddressState = source.readString();
        this.currentAddressCity = source.readString();
        this.currentAddressPinCode = source.readString();
        this.permanentAddressLine1 = source.readString();
        this.permanentAddressLine2 = source.readString();
        this.permanentAddressLandmark = source.readString();
        this.permanentAddressState = source.readString();
        this.permanentAddressCity = source.readString();
        this.permanentAddressPinCode = source.readString();
        this.addressProof = source.readString();
    }

    public PersonalDetails() {
    }

    protected PersonalDetails(Parcel in) {
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.middleName = in.readString();
        this.motherName = in.readString();
        this.spouseName = in.readString();
        this.fatherName = in.readString();
        this.educationalQualification = in.readString();
        this.formattedDate = in.readString();
        this.phoneNumber = in.readString();
        this.email = in.readString();
        this.aadhaarNumber = in.readString();
        this.panNumber = in.readString();
        this.maritalStatus = in.readString();
        this.gender = in.readString();
        this.permanentAddressStayingFor = in.readString();
        this.permanentAddressType = in.readString();
        this.dateOfBirth = in.readString();
        this.currentAddressLine1 = in.readString();
        this.currentAddressLine2 = in.readString();
        this.currentAddressLandmark = in.readString();
        this.currentAddressState = in.readString();
        this.currentAddressCity = in.readString();
        this.currentAddressPinCode = in.readString();
        this.permanentAddressLine1 = in.readString();
        this.permanentAddressLine2 = in.readString();
        this.permanentAddressLandmark = in.readString();
        this.permanentAddressState = in.readString();
        this.permanentAddressCity = in.readString();
        this.permanentAddressPinCode = in.readString();
        this.addressProof = in.readString();
    }

    public static final Parcelable.Creator<PersonalDetails> CREATOR = new Parcelable.Creator<PersonalDetails>() {
        @Override
        public PersonalDetails createFromParcel(Parcel source) {
            return new PersonalDetails(source);
        }

        @Override
        public PersonalDetails[] newArray(int size) {
            return new PersonalDetails[size];
        }
    };
}
