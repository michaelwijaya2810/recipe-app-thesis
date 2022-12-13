package com.irmwrs.recipeapp.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderIngredient {
    @SerializedName("Ingredientid")
    @Expose
    public long ingredientid;
    @SerializedName("Qty")
    @Expose
    public int qty;
}
