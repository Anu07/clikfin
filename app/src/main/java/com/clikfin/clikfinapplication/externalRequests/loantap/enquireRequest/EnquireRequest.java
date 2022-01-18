
package com.clikfin.clikfinapplication.externalRequests.loantap.enquireRequest;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class EnquireRequest {

    @SerializedName("documents_status")
    private DocumentsStatus documentsStatus;

    public DocumentsStatus getDocumentsStatus() {
        return documentsStatus;
    }

    public void setDocumentsStatus(DocumentsStatus documentsStatus) {
        this.documentsStatus = documentsStatus;
    }

}
