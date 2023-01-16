package com.irmwrs.recipeapp.settings.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangePassword {
    @SerializedName("NewPassword")
    @Expose
    public String newPassword;
    @SerializedName("OldPassword")
    @Expose
    public String oldPassword;
    @SerializedName("Userid")
    @Expose
    public String userid;
}
