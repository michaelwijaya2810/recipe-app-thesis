package com.irmwrs.recipeapp.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Validate {
    @SerializedName("AccessToken")
    @Expose
    public String accessToken;
    @SerializedName("Password")
    @Expose
    public String password;
}
