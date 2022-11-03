package com.irmwrs.recipeapp;

import com.irmwrs.recipeapp.Class.ChangePassword;
import com.irmwrs.recipeapp.Class.ResponseClass.Response;
import com.irmwrs.recipeapp.Class.ResponseClass.LoginResponse;
import com.irmwrs.recipeapp.Class.ResponseClass.RecipeListResponse;
import com.irmwrs.recipeapp.Class.ResponseClass.SingleRecipeResponse;
import com.irmwrs.recipeapp.Class.ResponseClass.UserResponse;
import com.irmwrs.recipeapp.Class.UpdateRecipe;
import com.irmwrs.recipeapp.Class.UserRegister;
import com.irmwrs.recipeapp.Class.Validate;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Server {
    String baseUrl = "https://personal-tskklq20.outsystemscloud.com/AdminPanel/rest/";
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    OutSystemService outSystemService = retrofit.create(OutSystemService.class);

    // Recipe
    Call<RecipeListResponse> getRecipeListFromKeyword(String keyword){
        Call<RecipeListResponse> call = outSystemService.getList(keyword);
        return call;
    }

    Call<RecipeListResponse> getHighlightedList(){
        Call<RecipeListResponse> call = outSystemService.getHighlightedList();
        return call;
    }

    Call<SingleRecipeResponse> getRecipeFromId(long recipeId){
        Call<SingleRecipeResponse> call = outSystemService.getSingleRecipe(recipeId);
        return call;
    }

    Call<UpdateRecipe> postCreateOrUpdateRecipe(UpdateRecipe updateRecipe){
        Call<UpdateRecipe> call = outSystemService.postCreateOrUpdateRecipe(updateRecipe);
        return call;
    }

    // Validate user
    Call<LoginResponse> postValidateUser(String username, Validate validate){
        Call<LoginResponse> call = outSystemService.postValidateOrLogin(username, validate);
        return call;
    }

    Call<LoginResponse> postLogin(String username, String password){
        Validate validate = new Validate();
        validate.password = password;
        Call<LoginResponse> call = outSystemService.postValidateOrLogin(username, validate);
        return call;
    }

    // User
    Call<LoginResponse> postRegister(UserRegister userRegister){
        Call<LoginResponse> call = outSystemService.postRegister(userRegister);
        return call;
    }

    Call<UserResponse> postUserDetail(int id){
        Call<UserResponse> call = outSystemService.postUserDetail(id);
        return call;
    }

    Call<Response> getForgotPassword(String username){
        Call<Response> call = outSystemService.getForgotPassword(username);
        return call;
    }

    Call<LoginResponse> postChangePassword(ChangePassword changePassword){
        Call<LoginResponse> call = outSystemService.postChangePassword(changePassword);
        return call;
    }

}
