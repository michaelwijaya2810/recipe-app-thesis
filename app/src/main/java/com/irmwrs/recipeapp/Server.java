package com.irmwrs.recipeapp;

import android.os.StrictMode;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.irmwrs.recipeapp.settings.models.ChangePassword;
import com.irmwrs.recipeapp.Class.Ingredient;
import com.irmwrs.recipeapp.Class.Key;
import com.irmwrs.recipeapp.Class.Order;
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
import com.irmwrs.recipeapp.cart.CartOrderResponse;
import com.irmwrs.recipeapp.payment.PaymentResponse;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
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

    public Server(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    // Recipe
    public Call<RecipeListResponse> getRecipeListFromKeyword(String keyword){
        Call<RecipeListResponse> call = outSystemService.getList(keyword);
        return call;
    }

    public Call<RecipeListResponse> getHighlightedList(){
        Call<RecipeListResponse> call = outSystemService.getHighlightedList();
        return call;
    }

    public Call<SingleRecipeResponse> getRecipeFromId(long recipeId, int userId){
        Call<SingleRecipeResponse> call = outSystemService.getSingleRecipe(recipeId, userId);
        return call;
    }

    public Call<Response> postCreateOrUpdateRecipe(UpdateRecipe updateRecipe, String auth){
        List<Key> result = generateKeysObject(updateRecipe);
        Call<Response> call = outSystemService.postCreateOrUpdateRecipe(auth, updateRecipe);
        return call;
    }

    public Call<List<Recipe>> getAllRecipe(){
        Call<List<Recipe>> call = outSystemService.getAllRecipeList();
        return call;
    }
    public Call<Response> postRating(long recipeId, int userId, String authToken, Review review){
        Call<Response> call = outSystemService.postRatingRecipe(recipeId, userId, authToken, review);
        return call;
    }

    // Ingredient
    public Call<List<Ingredient>> getAllIngredient(){
        Call<List<Ingredient>> call = outSystemService.getIngredientList();
        return call;
    }

    // Validate user
    public Call<LoginResponse> postValidateUser(String username, Validate validate){
        Call<LoginResponse> call = outSystemService.postValidateOrLogin(username, validate);
        return call;
    }

    public Call<LoginResponse> postLogin(String username, String password){
        Validate validate = new Validate();
        validate.password = password;
        Call<LoginResponse> call = outSystemService.postValidateOrLogin(username, validate);
        return call;
    }

    public Call<Key> getAuthToken(int userId, Object object){
        List<Key> keys = generateKeysObject(object);
        keys.get(0).value = toMd5(keys.get(0).value);
        Call<Key> call = outSystemService.postGenerateAuth(userId, keys);
        return call;
    }

    // User
    public Call<LoginResponse> postRegister(UserRegister userRegister){
        Call<LoginResponse> call = outSystemService.postRegister(userRegister);
        return call;
    }

    public Call<UserResponse> postUserDetail(int id){
        Call<UserResponse> call = outSystemService.postUserDetail(id);
        return call;
    }

    public Call<Response> getForgotPassword(String username){
        Call<Response> call = outSystemService.getForgotPassword(username);
        return call;
    }

    public Call<Response> postChangePassword(ChangePassword changePassword){
        Call<Response> call = outSystemService.postChangePassword(changePassword);
        return call;
    }

    // Order
    public Call<Response> postOrder(int userId, String authKey, Order order){
        Call<Response> call = outSystemService.postNewOrder(userId, authKey, order);
        return call;
    }

    public Call<List<CartOrderResponse>> getCart(int userId){
        Call<List<CartOrderResponse>> call = outSystemService.getCartOrder(userId);
        return call;
    }

    public Call<List<com.irmwrs.recipeapp.order.models.OrderHistoryResponse>> getOrderHistory(int userId){
        Call<List<com.irmwrs.recipeapp.order.models.OrderHistoryResponse>> call = outSystemService.getOrderHistory(userId);
        return call;
    }

    public Call<Response> getConfirmDelivery(int userId, String orderId){
        Call<Response> call = outSystemService.getConfirmDelivery(userId, orderId);
        return call;
    }

    // Payment
    public Call<PaymentResponse> postCheckout(long userId, String authKey, int totalPrice, List<Integer> orderListInt){
        List<Long> orderList = new ArrayList<>();
        for (int i = 0; i < orderListInt.size(); i++){ // change int to long because intent can't send an array of Long
            orderList.add(Long.valueOf(orderListInt.get(i)));
        }

        Call<PaymentResponse> call = outSystemService.postProccessCheckout(userId, authKey, totalPrice, orderList);
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
            Log.i("testRecipe", hexString.toString());
            return hexString.toString();
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return "";
    }

    List<Key> generateKeysObject(Object object){ // generate keys object with json in it
        Gson gson = new Gson();
        String value = gson.toJson(object);
        Log.i("testRecipe", value);
        List<Key> keys = new ArrayList<>();
        Key key = new Key();
        key.value = value;
        keys.add(key);
        return keys;
    }

}
