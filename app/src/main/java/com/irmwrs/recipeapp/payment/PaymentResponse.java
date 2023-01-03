package com.irmwrs.recipeapp.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.irmwrs.recipeapp.Class.ResponseClass.Response;

public class PaymentResponse {
    @SerializedName("ResponseStructStructure")
    @Expose
    public Response response;
    @SerializedName("PaymentInfo")
    @Expose
    public PaymentInfo paymentInfo;
}
