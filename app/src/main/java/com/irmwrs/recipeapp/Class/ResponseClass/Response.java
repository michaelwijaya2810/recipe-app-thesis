package com.irmwrs.recipeapp.Class.ResponseClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("AccessToken")
    @Expose
    public String accessToken;
    @SerializedName("Userid")
    @Expose
    public int userid;
    @SerializedName("ErrorReason")
    @Expose
    public String errorReason;
    @SerializedName("Response")
    @Expose
    public String response;

}