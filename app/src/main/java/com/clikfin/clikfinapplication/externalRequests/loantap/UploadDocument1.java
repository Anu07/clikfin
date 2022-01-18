
package com.clikfin.clikfinapplication.externalRequests.loantap;

import com.google.gson.annotations.SerializedName;

public class UploadDocument1 {

    @SerializedName("application_id")
    private String mApplicationId;
    @SerializedName("ext")
    private String mExt;
    @SerializedName("file")
    private String mFile;
    @SerializedName("file_name")
    private String mFileName;

    public String getApplicationId() {
        return mApplicationId;
    }

    public void setApplicationId(String applicationId) {
        mApplicationId = applicationId;
    }

    public String getExt() {
        return mExt;
    }

    public void setExt(String ext) {
        mExt = ext;
    }

    public String getFile() {
        return mFile;
    }

    public void setFile(String file) {
        mFile = file;
    }

    public String getFileName() {
        return mFileName;
    }

    public void setFileName(String fileName) {
        mFileName = fileName;
    }

}
