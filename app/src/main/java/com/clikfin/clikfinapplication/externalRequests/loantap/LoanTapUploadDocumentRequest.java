
package com.clikfin.clikfinapplication.externalRequests.loantap;

import com.clikfin.clikfinapplication.externalRequests.Response.loantapResponse.addApplication.Error;
import com.google.gson.annotations.SerializedName;

public class LoanTapUploadDocumentRequest {

    @SerializedName("upload_document+1")
    private UploadDocument1 mUploadDocument1;

    @SerializedName("error")
    private Error error;

    public UploadDocument1 getUploadDocument1() {
        return mUploadDocument1;
    }

    public void setUploadDocument1(UploadDocument1 uploadDocument1) {
        mUploadDocument1 = uploadDocument1;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public Error getError() {
        return error;
    }
}
