
package com.clikfin.clikfinapplication.externalRequests.Response.loantapResponse.EnquireResponse;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Kyc {

    @SerializedName("aadhaar_card")
    private AadhaarCard aadhaarCard;
    @SerializedName("aadhaar_card-xxxx")
    private AadhaarCardXxxx aadhaarCardXxxx;
    @SerializedName("address_proof")
    private AddressProof addressProof;
    @SerializedName("is_completed")
    private String isCompleted;

    public AadhaarCard getAadhaarCard() {
        return aadhaarCard;
    }

    public void setAadhaarCard(AadhaarCard aadhaarCard) {
        this.aadhaarCard = aadhaarCard;
    }

    public AadhaarCardXxxx getAadhaarCardXxxx() {
        return aadhaarCardXxxx;
    }

    public void setAadhaarCardXxxx(AadhaarCardXxxx aadhaarCardXxxx) {
        this.aadhaarCardXxxx = aadhaarCardXxxx;
    }

    public AddressProof getAddressProof() {
        return addressProof;
    }

    public void setAddressProof(AddressProof addressProof) {
        this.addressProof = addressProof;
    }

    public String getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(String isCompleted) {
        this.isCompleted = isCompleted;
    }

}
