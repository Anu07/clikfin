
package com.clikfin.clikfinapplication.externalRequests.Response.loantapResponse.EnquireResponse;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Answer {

    @SerializedName("doc_combo")
    private String docCombo;
    @Expose
    private Documents documents;

    public String getDocCombo() {
        return docCombo;
    }

    public void setDocCombo(String docCombo) {
        this.docCombo = docCombo;
    }

    public Documents getDocuments() {
        return documents;
    }

    public void setDocuments(Documents documents) {
        this.documents = documents;
    }

}
