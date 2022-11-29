package com.irmwrs.recipeapp;

import com.irmwrs.recipeapp.Class.ChangePassword;
import com.irmwrs.recipeapp.Class.Ingredient;
import com.irmwrs.recipeapp.Class.Recipe;
import com.irmwrs.recipeapp.Class.ResponseClass.Response;
import com.irmwrs.recipeapp.Class.ResponseClass.LoginResponse;
import com.irmwrs.recipeapp.Class.ResponseClass.RecipeListResponse;
import com.irmwrs.recipeapp.Class.ResponseClass.SingleRecipeResponse;
import com.irmwrs.recipeapp.Class.ResponseClass.UserResponse;
import com.irmwrs.recipeapp.Class.UpdateRecipe;
import com.irmwrs.recipeapp.Class.UserRegister;
import com.irmwrs.recipeapp.Class.Validate;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface OutSystemService {
    // Recipe
    @GET("Recipe/List")
    Call<RecipeListResponse> getList(@Query("Keyword") String keyword);
    @GET("Recipe/HighlightedList")
    Call<RecipeListResponse> getHighlightedList();
    @GET("Recipe/GetSingleRecipe")
    Call<SingleRecipeResponse> getSingleRecipe(@Query("RecipeId") long recipeId);
    @POST("Recipe/CreateOrUpdateRecipe")
    Call<UpdateRecipe> postCreateOrUpdateRecipe(@Body UpdateRecipe updateRecipe);
    @GET("Recipe/GetAllRecipeList")
    Call<List<Recipe>> getAllRecipeList();

    // Ingredient
    @GET("Recipe/GetIngredientList")
    Call<List<Ingredient>> getIngredientList();

    // Validate user
    @POST("ValidateUser/ValidateOrLogin")
    Call<LoginResponse> postValidateOrLogin(@Query("Username") String username, @Body Validate validate);

    // User
    @POST("User/Register")
    Call<LoginResponse> postRegister(@Body UserRegister userRegister);
    @POST("User/UserDetail")
    Call<UserResponse> postUserDetail(@Body int id);
    @GET("User/ForgotPassword")
    Call<Response> getForgotPassword(@Query("Username") String username);
    @POST("User/ChangePassword")
    Call<LoginResponse> postChangePassword(@Body ChangePassword changePassword);
}
