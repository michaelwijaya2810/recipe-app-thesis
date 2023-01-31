package com.irmwrs.recipeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;
import com.irmwrs.recipeapp.Class.Recipe;
import com.irmwrs.recipeapp.cart.views.CartFragment;
import com.irmwrs.recipeapp.cart.models.CartOrderResponse;
import com.irmwrs.recipeapp.fragments.RecipeListFragment;
import com.irmwrs.recipeapp.home.views.HomeFragment;
import com.irmwrs.recipeapp.order.models.OrderHistoryResponse;
import com.irmwrs.recipeapp.order.views.OrderFragment;
import com.irmwrs.recipeapp.settings.views.SettingsFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener{

    List<Recipe> recipes = new ArrayList<>();
    public NavigationBarView bottomNav;
    Server server = new Server();
    Fragment fragment = null;
    Functions functions = new Functions(MainActivity.this);
    int pageNumber;
    SwipeRefreshLayout refresh;
    int userId;
    Context context;
    SharedPreferences sharedPref;
    String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        pageNumber = intent.getIntExtra("pageNumber", 3);
        context = getApplicationContext();
        sharedPref = context.getSharedPreferences("userinfo",Context.MODE_PRIVATE);
        userId = sharedPref.getInt("Userid",0);
        address = sharedPref.getString("Address", "");
        bottomNav = findViewById(R.id.bottom_nav);
        refresh = findViewById(R.id.refresh);

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

        if(pageNumber == 1){
            bottomNav.setSelectedItemId(R.id.menu_1);
            RecipeFragment(false, 1);
        }
        else if(pageNumber == 2){
            bottomNav.setSelectedItemId(R.id.menu_2);
            CartFragment();
        }
        else if(pageNumber == 3){
            bottomNav.setSelectedItemId(R.id.menu_3);
            HomeFragment();
        }
        else if(pageNumber == 4){
            bottomNav.setSelectedItemId(R.id.menu_4);
            OrderFragment();
        }
        else if(pageNumber == 5){
            bottomNav.setSelectedItemId(R.id.menu_5);
            SettingsFragment();
        }
        bottomNav.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_1:
                // Recipe
                pageNumber=1;
                RecipeFragment(false, 1);
                return true;
            case R.id.menu_2:
                // Cart
                pageNumber =2;
                CartFragment();
                return true;
            case R.id.menu_3:
                // Home
                //Go to Home
                pageNumber = 3;
                HomeFragment();
                return true;
            case R.id.menu_4:
                // Order
                pageNumber = 4;
                OrderFragment();
                return true;
            case R.id.menu_5:
                //Settings
                pageNumber = 5;
                SettingsFragment();
                return true;
        }
        return false;
    }

    public void RecipeFragment(boolean isSearch, int filter){
        if(recipes.size() == 0){
            functions.showLoading();
            server.getAllRecipe().enqueue(new Callback<List<Recipe>>() {
                @Override
                public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                    if (!response.isSuccessful()){
                        functions.dismissLoading();
                        functions.showToast(String.valueOf(response.code()));
                        return;
                    }
                    recipes = response.body();
                    Collections.reverse(recipes);
                    fragment = new RecipeListFragment(response.body(), MainActivity.this, isSearch, filter);
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
                    functions.showToast(t.getMessage());
                }
            });
        }
        else {
            fragment = new RecipeListFragment(recipes, MainActivity.this, isSearch, filter);
            //Go to Recipe
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    void CartFragment(){
        if(!isLogin()){
            navigateToLogin();
            return;
        }
        functions.showLoading();
        server.getCart(userId).enqueue(new Callback<List<CartOrderResponse>>() {
            @Override
            public void onResponse(Call<List<CartOrderResponse>> call, Response<List<CartOrderResponse>> response) {
                if (!response.isSuccessful()) {
                    functions.dismissLoading();
                    functions.showToast(String.valueOf(response.code()));
                    return;
                }
                fragment = new CartFragment(response.body(), MainActivity.this);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
                functions.dismissLoading();
            }

            @Override
            public void onFailure(Call<List<CartOrderResponse>> call, Throwable t) {
                functions.dismissLoading();
                functions.showToast(t.getMessage());
            }
        });
    }

    public void HomeFragment(){
        if(recipes.size() == 0){
            functions.showLoading();
            server.getAllRecipe().enqueue(new Callback<List<Recipe>>() {
                @Override
                public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                    if (!response.isSuccessful()){
                        functions.dismissLoading();
                        functions.showToast(String.valueOf(response.code()));
                        return;
                    }
                    recipes = response.body();
                    Collections.reverse(recipes);
                    fragment = new HomeFragment(response.body());
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .commit();
                    functions.dismissLoading();
                }

                @Override
                public void onFailure(Call<List<Recipe>> call, Throwable t) {
                    functions.dismissLoading();
                    functions.showToast(t.getMessage());
                }
            });
        }
        else {
            fragment = new HomeFragment(recipes);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    public void OrderFragment(){
        if(!isLogin()){
            navigateToLogin();
            return;
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

    public void SettingsFragment(){
        if(!isLogin()){
            navigateToLogin();
            return;
        }
        fragment = new SettingsFragment(userId, address, MainActivity.this);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    boolean isLogin(){
        return userId != 0;
    }

    void navigateToLogin(){
        functions.showToast("Invalid Login Session");
        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);
    }

    public void refresh(){
        recipes.removeAll(recipes);
        address = sharedPref.getString("Address", "");

        if (pageNumber == 1) {
            RecipeFragment(false, 1);
            refresh.setRefreshing(false);
        } else if(pageNumber == 2){
            CartFragment();
            refresh.setRefreshing(false);
        } else if(pageNumber == 3){
            HomeFragment();
            refresh.setRefreshing(false);
        } else if(pageNumber == 4){
            OrderFragment();
            refresh.setRefreshing(false);
        } else if(pageNumber == 5){
            SettingsFragment();
            refresh.setRefreshing(false);
        }
    }
}