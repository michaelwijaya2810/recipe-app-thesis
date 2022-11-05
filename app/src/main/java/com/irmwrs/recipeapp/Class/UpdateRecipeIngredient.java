package com.irmwrs.recipeapp.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateRecipeIngredient {
    @SerializedName("Id")
    @Expose
    public long id;
    @SerializedName("RecipesId")
    @Expose
    public long recipesId;
    @SerializedName("IngredientId")
    @Expose
    public long ingredientId;
    @SerializedName("Qty")
    @Expose
    public int qty;
    @SerializedName("IsOptional")
    @Expose
    public Boolean isOptional;

}
