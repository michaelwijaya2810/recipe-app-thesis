package com.irmwrs.recipeapp;

import com.irmwrs.recipeapp.Class.ResponseClass.RecipeListResponse;
import com.irmwrs.recipeapp.Class.ResponseClass.SingleRecipeResponse;
import com.irmwrs.recipeapp.Class.UpdateRecipe;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface OutSystemService {
    @GET("Recipe/List")
    Call<RecipeListResponse> getList(@Query("Keyword") String keyword);
    @GET("Recipe/HighlightedList")
    Call<RecipeListResponse> getHighlightedList();
    @GET("Recipe/GetSingleRecipe")
    Call<SingleRecipeResponse> getSingleRecipe(@Query("RecipeId") long recipeId);
    @POST("Recipe/CreateOrUpdateRecipe")
    Call<UpdateRecipe> postCreateOrUpdateRecipe(@Body UpdateRecipe updateRecipe);
}
