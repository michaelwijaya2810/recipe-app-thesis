package com.irmwrs.recipeapp.order.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetail {

    @SerializedName("IngredientName")
    @Expose
    public String ingredientName;
    @SerializedName("IngredientImage")
    @Expose
    public String ingredientImage;
    @SerializedName("IngredientPrice")
    @Expose
    public int ingredientPrice;
    @SerializedName("IngredientQty")
    @Expose
    public int ingredientQty;
}
