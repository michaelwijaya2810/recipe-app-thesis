package com.irmwrs.recipeapp.settings.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangeAddress {
    @SerializedName("Address")
    @Expose
    public String address;
}
