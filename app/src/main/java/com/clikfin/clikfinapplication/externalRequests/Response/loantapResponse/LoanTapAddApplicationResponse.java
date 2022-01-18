
package com.clikfin.clikfinapplication.externalRequests.Response.loantapResponse;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class LoanTapAddApplicationResponse {

    @SerializedName("add_application+1")
    private AddApplication1 mAddApplication1;

    public AddApplication1 getAddApplication1() {
        return mAddApplication1;
    }

    public void setAddApplication1(AddApplication1 addApplication1) {
        mAddApplication1 = addApplication1;
    }

}
