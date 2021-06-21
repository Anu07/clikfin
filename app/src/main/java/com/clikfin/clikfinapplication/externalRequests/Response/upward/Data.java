package com.clikfin.clikfinapplication.externalRequests.Response.upward;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Data implements Parcelable {

	@SerializedName("error_message")
	private String errorMessage;

	@SerializedName("loan_data")
	private LoanData loanData;

	@SerializedName("missing_mandatory_keys")
	private List<String> missingMandatoryKeys;

	@SerializedName("is_success")
	private boolean isSuccess;

	public String getErrorMessage(){
		return errorMessage;
	}

	public LoanData getLoanData(){
		return loanData;
	}

	public List<String> getMissingMandatoryKeys(){
		return missingMandatoryKeys;
	}

	public boolean isIsSuccess(){
		return isSuccess;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.errorMessage);
		dest.writeParcelable(this.loanData, flags);
		dest.writeStringList(this.missingMandatoryKeys);
		dest.writeByte(this.isSuccess ? (byte) 1 : (byte) 0);
	}

	public void readFromParcel(Parcel source) {
		this.errorMessage = source.readString();
		this.loanData = source.readParcelable(LoanData.class.getClassLoader());
		this.missingMandatoryKeys = source.createStringArrayList();
		this.isSuccess = source.readByte() != 0;
	}

	public Data() {
	}

	protected Data(Parcel in) {
		this.errorMessage = in.readString();
		this.loanData = in.readParcelable(LoanData.class.getClassLoader());
		this.missingMandatoryKeys = in.createStringArrayList();
		this.isSuccess = in.readByte() != 0;
	}

	public static final Parcelable.Creator<Data> CREATOR = new Parcelable.Creator<Data>() {
		@Override
		public Data createFromParcel(Parcel source) {
			return new Data(source);
		}

		@Override
		public Data[] newArray(int size) {
			return new Data[size];
		}
	};
}