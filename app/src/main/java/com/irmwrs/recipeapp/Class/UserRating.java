package com.irmwrs.recipeapp.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserRating {
    @SerializedName("Reviews")
    @Expose
    public String reviews;
    @SerializedName("Rating")
    @Expose
    public int rating;
}
