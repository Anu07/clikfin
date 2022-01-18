
package com.clikfin.clikfinapplication.externalRequests.Response.upward.docUpload;

import com.google.gson.annotations.Expose;

@SuppressWarnings("unused")
public class DocumentURLGenerationResponse {

    @Expose
    private Data data;
    @Expose
    private String meta;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

}
