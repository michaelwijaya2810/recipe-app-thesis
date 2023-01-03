package com.irmwrs.recipeapp.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Review {
    @SerializedName("Id")
    @Expose
    public long id;
    @SerializedName("RecipeReview")
    @Expose
    public String recipeReview;
    @SerializedName("RecipesId")
    @Expose
    public long recipesId;
    @SerializedName("RecipeRating")
    @Expose
    public int recipeRating;
    @SerializedName("ReviewerId")
    @Expose
    public int reviewerId;
    @SerializedName("CreatedDate")
    @Expose
    public String createdDate;
}
