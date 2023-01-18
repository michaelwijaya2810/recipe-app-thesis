package com.irmwrs.recipeapp.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ingredient {
    @SerializedName("Id")
    @Expose
    public long id;
    @SerializedName("IngredientName")
    @Expose
    public String ingredientName;
    @SerializedName("IngredientPrice")
    @Expose
    public int ingredientPrice;
    @SerializedName("IsSoldOut")
    @Expose
    public boolean isSoldOut;
    @SerializedName("UOM")
    @Expose
    public int uom;
    @SerializedName("Image")
    @Expose
    public String ingredientImage;
}
