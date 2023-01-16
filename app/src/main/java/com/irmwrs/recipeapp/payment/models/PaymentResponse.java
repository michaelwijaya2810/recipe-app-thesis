package com.irmwrs.recipeapp.payment.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.irmwrs.recipeapp.Class.ResponseClass.Response;
import com.irmwrs.recipeapp.payment.models.PaymentInfo;

public class PaymentResponse {
    @SerializedName("ResponseStructStructure")
    @Expose
    public Response response;
    @SerializedName("PaymentInfo")
    @Expose
    public PaymentInfo paymentInfo;
}
