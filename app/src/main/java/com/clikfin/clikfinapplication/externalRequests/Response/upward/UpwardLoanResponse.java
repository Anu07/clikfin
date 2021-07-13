package com.clikfin.clikfinapplication.externalRequests.Response.upward;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class UpwardLoanResponse implements Parcelable {

	@SerializedName("data")
	private Data data;

	@SerializedName("meta")
	private String meta;

	public Data getData(){
		return data;
	}

	public String getMeta(){
		return meta;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(this.data, flags);
		dest.writeString(this.meta);
	}

	public void readFromParcel(Parcel source) {
		this.data = source.readParcelable(Data.class.getClassLoader());
		this.meta = source.readString();
	}

	public UpwardLoanResponse(Parcel in) {
		this.data = in.readParcelable(Data.class.getClassLoader());
		this.meta = in.readString();
	}

	public static final Parcelable.Creator<UpwardLoanResponse> CREATOR = new Parcelable.Creator<UpwardLoanResponse>() {
		@Override
		public UpwardLoanResponse createFromParcel(Parcel source) {
			return new UpwardLoanResponse(source);
		}

		@Override
		public UpwardLoanResponse[] newArray(int size) {
			return new UpwardLoanResponse[size];
		}
	};
}