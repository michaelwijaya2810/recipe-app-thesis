package com.irmwrs.recipeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.irmwrs.recipeapp.Class.ResponseClass.SingleRecipeResponse;
import com.irmwrs.recipeapp.Class.ResponseClass.UserResponse;
import com.irmwrs.recipeapp.adapters.RecipeDetailAdapter;
import com.irmwrs.recipeapp.fragments.RecipeListFragment;
import com.irmwrs.recipeapp.order.adapters.OrderAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeDetailActivity extends AppCompatActivity {

    TabLayout tabLayout;
    TabLayoutMediator mediator;
    ViewPager2 recipeDetailPager;
    RecipeDetailAdapter recipeDetailAdapter;
    boolean isLogin = true;
    int userId; // todo get user id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Intent intent = getIntent();
        int recipeId = intent.getIntExtra("recipeId", 0);
        userId = intent.getIntExtra("userId", 0);

        // sample data
        userId = 9;

        if (userId == 0){
            isLogin = false;
        }

        Server server = new Server();
        Functions functions = new Functions(RecipeDetailActivity.this);
        functions.showLoading();
        server.getRecipeFromId(recipeId, userId).enqueue(new Callback<SingleRecipeResponse>() {
            @Override
            public void onResponse(Call<SingleRecipeResponse> call, Response<SingleRecipeResponse> response) {
                if (!response.isSuccessful()){
                    functions.dismissLoading();
                    functions.showToast(String.valueOf(response.code()));
                    return;
                }
                if(response.body().response.errorReason != null){
                    functions.dismissLoading();
                    functions.showToast(response.body().response.errorReason);
                    return;
                }
                SingleRecipeResponse singleRecipeResponse = response.body();
                server.postUserDetail(response.body().recipe.creatorId).enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        if (!response.isSuccessful()){
                            functions.dismissLoading();
                            functions.showToast(String.valueOf(response.code()));
                            return;
                        }
                        tabLayout = findViewById(R.id.tab_layout);
                        recipeDetailPager = findViewById(R.id.recipe_detail_pager);
                        recipeDetailAdapter = new RecipeDetailAdapter(getSupportFragmentManager(), getLifecycle(), singleRecipeResponse, response.body().username, isLogin, userId);
                        recipeDetailPager.setAdapter(recipeDetailAdapter);
                        mediator = new TabLayoutMediator(tabLayout, recipeDetailPager,
                                (tab, position) -> {
                                    if (position == 0) {
                                        tab.setText("Ingredients");
                                    } else if (position == 1) {
                                        tab.setText("Steps");
                                    }
                                });
                        mediator.attach();
                        functions.dismissLoading();
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        functions.dismissLoading();
                        functions.showToast(t.getMessage());
                    }
                });
            }

            @Override
            public void onFailure(Call<SingleRecipeResponse> call, Throwable t) {
                functions.dismissLoading();
                functions.showToast(t.getMessage());
            }
        });
    }
}