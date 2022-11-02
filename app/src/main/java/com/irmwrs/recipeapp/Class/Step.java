package com.irmwrs.recipeapp.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Step {
    @SerializedName("Id")
    @Expose
    public Long id;
    @SerializedName("RecipeId")
    @Expose
    public Long recipeId;
    @SerializedName("RecipeSteps")
    @Expose
    public String recipeSteps;
}
