package com.irmwrs.recipeapp.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Recipe {
    @SerializedName(value = "CreatorId", alternate = "CreatorID")
    @Expose
    public int creatorId;
    @SerializedName("Id")
    @Expose
    public int id;
    @SerializedName("RecipeStatus")
    @Expose
    public int recipeStatus;
    @SerializedName("RecipeDescription")
    @Expose
    public String recipeDescription;
    @SerializedName("RecipeName")
    @Expose
    public String recipeName;
    @SerializedName("RecipeRating")
    @Expose
    public String recipeRating;
    @SerializedName("RecipeImage")
    @Expose
    public String recipeImage;
    @SerializedName("IsActive")
    @Expose
    public boolean isActive;
    @SerializedName("RecipeDifficulty")
    @Expose
    public int recipeDifficulty;
    @SerializedName("IsHighlighted")
    @Expose
    public boolean isHighlighted;
}
