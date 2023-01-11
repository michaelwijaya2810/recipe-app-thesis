package com.irmwrs.recipeapp;

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
    Call<SingleRecipeResponse> getSingleRecipe(@Query("RecipeId") long recipeId, @Query("UserId") int userId);
    @POST("Recipe/CreateOrUpdateRecipe")
    Call<Response> postCreateOrUpdateRecipe(@Query("Auth") String auth, @Body UpdateRecipe updateRecipe);
    @GET("Recipe/GetAllRecipeList")
    Call<List<Recipe>> getAllRecipeList();
    @POST("Recipe/RatingRecipe")
    Call<Response> postRatingRecipe(@Query("RecipeId") long recipeId, @Query("UserId") int userId, @Query("AuthToken") String authToken, @Body Review review);

    // Ingredient
    @GET("Recipe/GetIngredientList")
    Call<List<Ingredient>> getIngredientList();

    // Validate user
    @POST("ValidateUser/ValidateOrLogin")
    Call<LoginResponse> postValidateOrLogin(@Query("Username") String username, @Body Validate validate);
    @POST("ValidateUser/GenerateAuth")
    Call<Key> postGenerateAuth(@Query("UserId") int userId, @Body List<Key> keys);

    // User
    @POST("User/Register")
    Call<LoginResponse> postRegister(@Body UserRegister userRegister);
    @POST("User/UserDetail")
    Call<UserResponse> postUserDetail(@Body int id);
    @GET("User/ForgotPassword")
    Call<Response> getForgotPassword(@Query("Username") String username);
    @POST("User/ChangePassword")
    Call<Response> postChangePassword(@Body ChangePassword changePassword);

    // Order
    @POST("Order/NewOrder")
    Call<Response> postNewOrder(@Query("userid") int userId, @Query("AuthKey") String authKey, @Body Order order);
    @GET("Order/GetCartOrder")
    Call<List<CartOrderResponse>> getCartOrder(@Query("Userid") int userId);
    @GET("Order/OrderHistory")
    Call<List<com.irmwrs.recipeapp.order.models.OrderHistoryResponse>> getOrderHistory(@Query("userid") int userId);
    @GET("Order/ConfirmDelivery")
    Call<Response> getConfirmDelivery(@Query("userid") int userId, @Query("orderid") String orderId);

    // Payment
    @POST("Payment/ProccessCheckout")
    Call<PaymentResponse> postProccessCheckout(@Query("UserId") long userId, @Query("Authkey") String authKey, @Query("TotalPrice") int totalPrice, @Body List<Long> orderList);


}
