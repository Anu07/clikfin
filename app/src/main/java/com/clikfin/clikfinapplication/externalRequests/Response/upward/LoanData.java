package com.clikfin.clikfinapplication.externalRequests.Response.upward;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class LoanData implements Parcelable {

	@SerializedName("affiliate_loan_identifier")
	private int affiliateLoanIdentifier;

	@SerializedName("customer_id")
	private int customerId;

	@SerializedName("loan_id")
	private int loanId;

	public int getAffiliateLoanIdentifier(){
		return affiliateLoanIdentifier;
	}

	public int getCustomerId(){
		return customerId;
	}

	public int getLoanId(){
		return loanId;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.affiliateLoanIdentifier);
		dest.writeInt(this.customerId);
		dest.writeInt(this.loanId);
	}

	public void readFromParcel(Parcel source) {
		this.affiliateLoanIdentifier = source.readInt();
		this.customerId = source.readInt();
		this.loanId = source.readInt();
	}

	public LoanData() {
	}

	protected LoanData(Parcel in) {
		this.affiliateLoanIdentifier = in.readInt();
		this.customerId = in.readInt();
		this.loanId = in.readInt();
	}

	public static final Parcelable.Creator<LoanData> CREATOR = new Parcelable.Creator<LoanData>() {
		@Override
		public LoanData createFromParcel(Parcel source) {
			return new LoanData(source);
		}

		@Override
		public LoanData[] newArray(int size) {
			return new LoanData[size];
		}
	};

	public void setAffiliateLoanIdentifier(int affiliateLoanIdentifier) {
		this.affiliateLoanIdentifier = affiliateLoanIdentifier;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}
}