
package com.clikfin.clikfinapplication.externalRequests.Response.loantapResponse.EnquireResponse;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class SalarySlip {

    @SerializedName("is_completed")
    private String isCompleted;
    @SerializedName("salary_slip-all-092021")
    private SalarySlipAll092021 salarySlipAll092021;

    public String getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(String isCompleted) {
        this.isCompleted = isCompleted;
    }

    public SalarySlipAll092021 getSalarySlipAll092021() {
        return salarySlipAll092021;
    }

    public void setSalarySlipAll092021(SalarySlipAll092021 salarySlipAll092021) {
        this.salarySlipAll092021 = salarySlipAll092021;
    }

}
