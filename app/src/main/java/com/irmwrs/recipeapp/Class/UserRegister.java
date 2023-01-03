package com.irmwrs.recipeapp.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserRegister {
    @SerializedName("Address")
    @Expose
    public String address;
    @SerializedName("FirstName")
    @Expose
    public String firstName;
    @SerializedName("LastName")
    @Expose
    public String lastName;
    @SerializedName("Password")
    @Expose
    public String password;
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
