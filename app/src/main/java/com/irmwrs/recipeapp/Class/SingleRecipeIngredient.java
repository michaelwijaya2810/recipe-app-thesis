package com.irmwrs.recipeapp.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SingleRecipeIngredient {
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
