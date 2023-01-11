package com.irmwrs.recipeapp.Class.ResponseClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("userid")
    @Expose
    public Integer userid;
    @SerializedName("Errorreason")
    @Expose
    public String errorreason;
    @SerializedName("username")
    @Expose
    public String username;
}
