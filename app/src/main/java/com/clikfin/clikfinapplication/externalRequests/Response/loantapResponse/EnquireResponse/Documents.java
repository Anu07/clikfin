
package com.clikfin.clikfinapplication.externalRequests.Response.loantapResponse.EnquireResponse;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Documents {

    @SerializedName("bank_statement")
    private BankStatement bankStatement;
    @Expose
    private Kyc kyc;
    @Expose
    private Others others;
    @SerializedName("salary_slip")
    private SalarySlip salarySlip;

    public BankStatement getBankStatement() {
        return bankStatement;
    }

    public void setBankStatement(BankStatement bankStatement) {
        this.bankStatement = bankStatement;
    }

    public Kyc getKyc() {
        return kyc;
    }

    public void setKyc(Kyc kyc) {
        this.kyc = kyc;
    }

    public Others getOthers() {
        return others;
    }

    public void setOthers(Others others) {
        this.others = others;
    }

    public SalarySlip getSalarySlip() {
        return salarySlip;
    }

    public void setSalarySlip(SalarySlip salarySlip) {
        this.salarySlip = salarySlip;
    }

}
