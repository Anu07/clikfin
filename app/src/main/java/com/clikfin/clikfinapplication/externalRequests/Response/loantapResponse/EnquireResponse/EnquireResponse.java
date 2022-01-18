
package com.clikfin.clikfinapplication.externalRequests.Response.loantapResponse.EnquireResponse;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class EnquireResponse {

    @SerializedName("documents_status")
    private DocumentsStatus documentsStatus;

    public DocumentsStatus getDocumentsStatus() {
        return documentsStatus;
    }

    public void setDocumentsStatus(DocumentsStatus documentsStatus) {
        this.documentsStatus = documentsStatus;
    }

}
