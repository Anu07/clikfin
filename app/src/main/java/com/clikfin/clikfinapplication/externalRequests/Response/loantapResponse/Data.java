
package com.clikfin.clikfinapplication.externalRequests.Response.loantapResponse;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Data {

    @SerializedName("lapp_id")
    private String mLappId;
    @SerializedName("link")
    private String mLink;

    public String getLappId() {
        return mLappId;
    }

    public void setLappId(String lappId) {
        mLappId = lappId;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String link) {
        mLink = link;
    }

}
