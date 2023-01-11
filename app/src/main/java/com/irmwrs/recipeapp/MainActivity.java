package com.irmwrs.recipeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationBarView;
import com.irmwrs.recipeapp.Class.Recipe;
import com.irmwrs.recipeapp.Class.ResponseClass.UserResponse;
import com.irmwrs.recipeapp.cart.CartFragment;
import com.irmwrs.recipeapp.cart.CartOrderResponse;
import com.irmwrs.recipeapp.fragments.RecipeListFragment;
import com.irmwrs.recipeapp.order.models.Order;
import com.irmwrs.recipeapp.order.models.OrderHistoryResponse;
import com.irmwrs.recipeapp.order.views.OrderFragment;
import com.irmwrs.recipeapp.settings.views.SettingsFragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener{

    NavigationBarView bottomNav;
    Server server = new Server();
    Fragment fragment = null;
    Functions functions = new Functions(MainActivity.this);

    int userId; // todo get user id



    Context context;
    SharedPreferences sharepref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        sharepref = context.getSharedPreferences("userinfo",Context.MODE_PRIVATE);

        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        int pageNumber = intent.getIntExtra("pageNumber", 3);

        bottomNav = findViewById(R.id.bottom_nav);
        if(pageNumber == 1){
            RecipeFragment();
            bottomNav.setSelectedItemId(R.id.menu_1);
        }
        else if(pageNumber == 2){
            CartFragment();
            bottomNav.setSelectedItemId(R.id.menu_2);
        }
        else if(pageNumber == 3){
            bottomNav.setSelectedItemId(R.id.menu_3);
        }
        else if(pageNumber == 4){
            OrderFragment();
            bottomNav.setSelectedItemId(R.id.menu_4);
        }
        else if(pageNumber == 5){
            SettingsFragment();
            bottomNav.setSelectedItemId(R.id.menu_5);
        }

        bottomNav.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_1:
                // Recipe
                RecipeFragment();
                return true;
            case R.id.menu_2:
                // Cart
                CartFragment();
                return true;
            case R.id.menu_3:
                // Home
                //Go to Home
                return true;
            case R.id.menu_4:
                // Order
                OrderFragment();
                return true;
            case R.id.menu_5:
                //Settings
                SettingsFragment();
                return true;
        }
        return false;
    }

    void RecipeFragment(){

        functions.showLoading();
        server.getAllRecipe().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (!response.isSuccessful()){
                    functions.dismissLoading();
                    Toast.makeText(getApplicationContext(), String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                    return;
                }
                fragment = new RecipeListFragment(response.body(), MainActivity.this);
                //Go to Recipe
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
                functions.dismissLoading();
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                functions.dismissLoading();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void CartFragment(){
        userId = sharepref.getInt("Userid",0);
        if(userId == 0)
        {
            Toast.makeText(context, "Invalid Login Session", Toast.LENGTH_SHORT).show();

            Intent intentlogin = new Intent(context, Login.class);
            startActivity(intentlogin);

        }
        functions.showLoading();
        server.getCart(userId).enqueue(new Callback<List<CartOrderResponse>>() {
            @Override
            public void onResponse(Call<List<CartOrderResponse>> call, Response<List<CartOrderResponse>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                    return;
                }
                fragment = new CartFragment(response.body());
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
                functions.dismissLoading();
            }

            @Override
            public void onFailure(Call<List<CartOrderResponse>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void OrderFragment(){
        userId = sharepref.getInt("Userid",0);
        if(userId == 0)
        {
            Toast.makeText(context, "Invalid Login Session", Toast.LENGTH_SHORT).show();

            Intent intentlogin = new Intent(context, Login.class);
            startActivity(intentlogin);

        }
        functions.showLoading();
        server.getOrderHistory(userId).enqueue(new Callback<List<OrderHistoryResponse>>() {
            @Override
            public void onResponse(Call<List<OrderHistoryResponse>> call, Response<List<OrderHistoryResponse>> response) {
                if (!response.isSuccessful()) {
                    functions.dismissLoading();
                    functions.showToast(String.valueOf(response.code()));
                    return;
                }
                fragment = new OrderFragment(response.body());
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
                functions.dismissLoading();
            }

            @Override
            public void onFailure(Call<List<OrderHistoryResponse>> call, Throwable t) {
                functions.dismissLoading();
                functions.showToast(t.getMessage());
            }
        });
    }

    void SettingsFragment(){
        userId = sharepref.getInt("Userid",0);
        if(userId == 0)
        {
            Toast.makeText(context, "Invalid Login Session", Toast.LENGTH_SHORT).show();

            Intent intentlogin = new Intent(context, Login.class);
            startActivity(intentlogin);

        }
        functions.showLoading();
        server.postUserDetail(userId).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (!response.isSuccessful()) {
                    functions.dismissLoading();
                    functions.showToast(String.valueOf(response.code()));
                    return;
                }
                String address = response.body().user.address;
                fragment = new SettingsFragment(userId, address, MainActivity.this);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
                functions.dismissLoading();
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                functions.dismissLoading();
                functions.showToast(t.getMessage());
            }
        });
    }

}