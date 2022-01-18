
package com.clikfin.clikfinapplication.externalRequests.Response.loantapResponse.EnquireResponse;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class BankStatement {

    @SerializedName("individual_files")
    private IndividualFiles individualFiles;
    @SerializedName("is_completed")
    private String isCompleted;
    @SerializedName("single_file")
    private SingleFile singleFile;

    public IndividualFiles getIndividualFiles() {
        return individualFiles;
    }

    public void setIndividualFiles(IndividualFiles individualFiles) {
        this.individualFiles = individualFiles;
    }

    public String getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(String isCompleted) {
        this.isCompleted = isCompleted;
    }

    public SingleFile getSingleFile() {
        return singleFile;
    }

    public void setSingleFile(SingleFile singleFile) {
        this.singleFile = singleFile;
    }

}
