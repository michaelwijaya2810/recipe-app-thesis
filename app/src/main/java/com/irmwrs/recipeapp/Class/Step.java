package com.irmwrs.recipeapp.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Step {
    @SerializedName("Id")
    @Expose
    public long id;
    @SerializedName("RecipeId")
    @Expose
    public long recipeId;
    @SerializedName("RecipeSteps")
    @Expose
    public String recipeSteps;

    public String rvId = ""; // RecyclerView id for updating recipe steps
}
