package com.irmwrs.recipeapp.Class;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.irmwrs.recipeapp.Ingredient;

public class UpdateRecipe {
    @SerializedName("RecipeId")
    @Expose
    public Long recipeId;
    @SerializedName("CreatorId")
    @Expose
    public String creatorId;
    @SerializedName("RecipeDescription")
    @Expose
    public String recipeDescription;
    @SerializedName("RecipeName")
    @Expose
    public String recipeName;
    @SerializedName("RecipeImage")
    @Expose
    public String recipeImage;
    @SerializedName("StepList")
    @Expose
    public List<Step> stepList = null;
    @SerializedName("RecipeDifficulty")
    @Expose
    public Integer recipeDifficulty;
    @SerializedName("IngredientList")
    @Expose
    public List<UpdateRecipeIngredient> ingredientList = null;

}
