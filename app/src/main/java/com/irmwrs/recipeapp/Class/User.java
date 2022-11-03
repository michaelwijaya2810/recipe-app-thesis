package com.irmwrs.recipeapp.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("Id")
    @Expose
    public Long id;
    @SerializedName("UserRole")
    @Expose
    public String userRole;
    @SerializedName("Userid")
    @Expose
    public Integer userid;
    @SerializedName("IsActive")
    @Expose
    public Boolean isActive;
    @SerializedName("Address")
    @Expose
    public String address;
    @SerializedName("FirstName")
    @Expose
    public String firstName;
    @SerializedName("LastName")
    @Expose
    public String lastName;
}
