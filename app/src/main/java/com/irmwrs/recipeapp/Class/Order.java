package com.irmwrs.recipeapp.Class;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {
    @SerializedName("RecipeId")
    @Expose
    public long recipeId;
    @SerializedName("RequestDeliveryDate")
    @Expose
    public String requestDeliveryDate;
    @SerializedName("Ingredient")
    @Expose
    public List<OrderIngredient> ingredient;
}
