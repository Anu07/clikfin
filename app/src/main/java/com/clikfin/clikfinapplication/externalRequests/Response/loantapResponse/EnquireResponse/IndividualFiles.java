
package com.clikfin.clikfinapplication.externalRequests.Response.loantapResponse.EnquireResponse;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class IndividualFiles {

    @SerializedName("bank_statement-072021")
    private BankStatement072021 bankStatement072021;
    @SerializedName("bank_statement-082021")
    private BankStatement082021 bankStatement082021;
    @SerializedName("bank_statement-092021")
    private BankStatement092021 bankStatement092021;

    public BankStatement072021 getBankStatement072021() {
        return bankStatement072021;
    }

    public void setBankStatement072021(BankStatement072021 bankStatement072021) {
        this.bankStatement072021 = bankStatement072021;
    }

    public BankStatement082021 getBankStatement082021() {
        return bankStatement082021;
    }

    public void setBankStatement082021(BankStatement082021 bankStatement082021) {
        this.bankStatement082021 = bankStatement082021;
    }

    public BankStatement092021 getBankStatement092021() {
        return bankStatement092021;
    }

    public void setBankStatement092021(BankStatement092021 bankStatement092021) {
        this.bankStatement092021 = bankStatement092021;
    }

}
