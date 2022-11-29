package com.irmwrs.recipeapp.Class;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateRecipe {
    @SerializedName("RecipeId")
    @Expose
    public long recipeId;
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
    public int recipeDifficulty;
    @SerializedName("IngredientList")
    @Expose
    public List<UpdateRecipeIngredient> ingredientList = null;

    public void setRecipeImage(Bitmap bitmapImage) {
        String image = "";
        if(bitmapImage != null){
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            byte[] bytes = outputStream.toByteArray();
            image = Base64.encodeToString(bytes, android.util.Base64.DEFAULT);
        }
        this.recipeImage = image;
    }
}
