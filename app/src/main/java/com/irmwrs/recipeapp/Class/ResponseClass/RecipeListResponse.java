package com.irmwrs.recipeapp.Class.ResponseClass;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.irmwrs.recipeapp.Class.Recipe;

public class RecipeListResponse {
    @SerializedName("response")
    @Expose
    public Response response;
    @SerializedName("Data")
    @Expose
    public List<Recipe> data = null;
}
