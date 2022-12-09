package com.irmwrs.recipeapp;

import android.util.JsonReader;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.irmwrs.recipeapp.Class.ChangePassword;
import com.irmwrs.recipeapp.Class.Ingredient;
import com.irmwrs.recipeapp.Class.Key;
import com.irmwrs.recipeapp.Class.Recipe;
import com.irmwrs.recipeapp.Class.ResponseClass.Response;
import com.irmwrs.recipeapp.Class.ResponseClass.LoginResponse;
import com.irmwrs.recipeapp.Class.ResponseClass.RecipeListResponse;
import com.irmwrs.recipeapp.Class.ResponseClass.SingleRecipeResponse;
import com.irmwrs.recipeapp.Class.ResponseClass.UserResponse;
import com.irmwrs.recipeapp.Class.Review;
import com.irmwrs.recipeapp.Class.UpdateRecipe;
import com.irmwrs.recipeapp.Class.UserRegister;
import com.irmwrs.recipeapp.Class.Validate;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Server {
    String baseUrl = "https://personal-tskklq20.outsystemscloud.com/AdminPanel/rest/";
    Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
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

    Call<SingleRecipeResponse> getRecipeFromId(long recipeId, int userId){
        Call<SingleRecipeResponse> call = outSystemService.getSingleRecipe(recipeId, userId);
        return call;
    }

    Call<Response> postCreateOrUpdateRecipe(UpdateRecipe updateRecipe, String auth){
        Call<Response> call = outSystemService.postCreateOrUpdateRecipe(auth, updateRecipe);
        return call;
    }

    Call<List<Recipe>> getAllRecipe(){
        Call<List<Recipe>> call = outSystemService.getAllRecipeList();
        return call;
    }
    Call<Response> postRating(long recipeId, int userId, String authToken, Review review){
        Call<Response> call = outSystemService.postRatingRecipe(recipeId, userId, authToken, review);
        return call;
    }

    // Ingredient
    Call<List<Ingredient>> getAllIngredient(){
        Call<List<Ingredient>> call = outSystemService.getIngredientList();
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

    Call<Key> getAuthToken(int userId, List<Key> keys){
        keys.get(0).value = toMd5(keys.get(0).value);
        Call<Key> call = outSystemService.postGenerateAuth(userId, keys);
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

    String toMd5(String value){ // md5 converter if string is too long
        String md5 = "MD5";
        try {
            MessageDigest digest = MessageDigest.getInstance(md5);
            digest.update(value.getBytes());
            byte valueDigest[] = digest.digest();

            StringBuilder hexString = new StringBuilder();

            for(byte aMsgDigest : valueDigest){
                String h = Integer.toHexString(0xFF & aMsgDigest);

                while (h.length()<2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return "";
    }

}
