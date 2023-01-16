package com.irmwrs.recipeapp.cart.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetail {
    @SerializedName("IngredientName")
    @Expose
    public String ingredientName;
    @SerializedName("IngredientPrice")
    @Expose
    public int ingredientPrice;
    @SerializedName("IngredientQty")
    @Expose
    public int ingredientQty;
}
