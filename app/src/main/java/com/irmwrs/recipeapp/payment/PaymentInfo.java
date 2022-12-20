package com.irmwrs.recipeapp.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentInfo {

    @SerializedName("BankName")
    @Expose
    public String bankName;
    @SerializedName("InvNumber")
    @Expose
    public String invNumber;
    @SerializedName("VirtualAccNumber")
    @Expose
    public String virtualAccNumber;
}
