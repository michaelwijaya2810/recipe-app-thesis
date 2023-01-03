package com.irmwrs.recipeapp.Class.ResponseClass;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.irmwrs.recipeapp.Class.Recipe;
import com.irmwrs.recipeapp.Class.SingleRecipeIngredient;
import com.irmwrs.recipeapp.Class.Step;
import com.irmwrs.recipeapp.Class.UserRating;

public class SingleRecipeResponse {
    @SerializedName("Response")
    @Expose
    public Response response;
    @SerializedName("Recipe")
    @Expose
    public Recipe recipe;
    @SerializedName("Steps")
    @Expose
    public List<Step> steps = null;
    @SerializedName("IsReviewed")
    @Expose
    public boolean isReviewed;
    @SerializedName("UserRating")
    @Expose
    public UserRating userRating;
    @SerializedName("Ingredients")
    @Expose
    public List<SingleRecipeIngredient> ingredients = null;
}
