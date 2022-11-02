package com.irmwrs.recipeapp;

import com.irmwrs.recipeapp.Class.ResponseClass.RecipeListResponse;
import com.irmwrs.recipeapp.Class.ResponseClass.SingleRecipeResponse;
import com.irmwrs.recipeapp.Class.UpdateRecipe;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Server {
    String baseUrl = "https://personal-tskklq20.outsystemscloud.com/AdminPanel/rest/";
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    Call<RecipeListResponse> getList(String keyword){
        OutSystemService outSystemService = retrofit.create(OutSystemService.class);
        Call<RecipeListResponse> call = outSystemService.getList(keyword);
        return call;
    }

    Call<RecipeListResponse> getHighlightedList(){
        OutSystemService outSystemService = retrofit.create(OutSystemService.class);
        Call<RecipeListResponse> call = outSystemService.getHighlightedList();
        return call;
    }

    Call<SingleRecipeResponse> getSingleRecipe(long recipeId){
        OutSystemService outSystemService = retrofit.create(OutSystemService.class);
        Call<SingleRecipeResponse> call = outSystemService.getSingleRecipe(recipeId);
        return call;
    }

    Call<UpdateRecipe> postCreateOrUpdateRecipe(UpdateRecipe updateRecipe){
        OutSystemService outSystemService = retrofit.create(OutSystemService.class);
        Call<UpdateRecipe> call = outSystemService.postCreateOrUpdateRecipe(updateRecipe);
        return call;
    }

}
