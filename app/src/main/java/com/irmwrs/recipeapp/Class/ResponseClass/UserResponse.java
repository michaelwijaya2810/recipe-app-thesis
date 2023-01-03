package com.irmwrs.recipeapp.Class.ResponseClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.irmwrs.recipeapp.Class.User;

public class UserResponse {
    @SerializedName("Response")
    @Expose
    public Response response;
    @SerializedName("user")
    @Expose
    public User user;
    @SerializedName("Username")
    @Expose
    public String username;
    @SerializedName("PhoneNumber")
    @Expose
    public String phoneNumber;
    @SerializedName("Email")
    @Expose
    public String email;
}
