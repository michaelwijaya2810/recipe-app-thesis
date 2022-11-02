package com.irmwrs.recipeapp.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SingleRecipeIngredient {
    @SerializedName("Id")
    @Expose
    public Long id;
    @SerializedName("RecipesId")
    @Expose
    public Long recipesId;
    @SerializedName("IngredientId")
    @Expose
    public Long ingredientId;
    @SerializedName("Qty")
    @Expose
    public Integer qty;
    @SerializedName("Isactive")
    @Expose
    public Boolean isactive;
    @SerializedName("IngredientImage")
    @Expose
    public String ingredientImage;
    @SerializedName("IngredientName")
    @Expose
    public String ingredientName;
}
