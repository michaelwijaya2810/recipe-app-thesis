package com.irmwrs.recipeapp.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Recipe {
    @SerializedName("Id")
    @Expose
    public long id;
    @SerializedName("RecipeName")
    @Expose
    public String recipeName;
    @SerializedName("CreatorID")
    @Expose
    public int creatorID;
    @SerializedName("IsActive")
    @Expose
    public Boolean isActive;
    @SerializedName("IsHighlighted")
    @Expose
    public Boolean isHighlighted;
    @SerializedName("RecipeDifficulty")
    @Expose
    public int recipeDifficulty;
    @SerializedName("RecipesStatus")
    @Expose
    public int recipesStatus;
    @SerializedName("RecipeImage")
    @Expose
    public String recipeImage;
    @SerializedName("RecipeDescription")
    @Expose
    public String recipeDescription;
}
