package com.irmwrs.recipeapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.irmwrs.recipeapp.Class.ResponseClass.SingleRecipeResponse;
import com.irmwrs.recipeapp.Class.ResponseClass.UserResponse;
import com.irmwrs.recipeapp.adapters.RecipeDetailAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeDetailActivity extends AppCompatActivity {

    TabLayout tabLayout;
    TabLayoutMediator mediator;
    ViewPager2 recipeDetailPager;
    Button btn_edit;
    RecipeDetailAdapter recipeDetailAdapter;
    boolean isLogin = true;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Intent intent = getIntent();
        int recipeId = intent.getIntExtra("recipeId", 0);

        btn_edit = findViewById(R.id.btn_edit);

        // sample data
        Context context = getApplicationContext();
        SharedPreferences sharepref = context.getSharedPreferences("userinfo",Context.MODE_PRIVATE);
        try {
            userId = sharepref.getInt("Userid",0);
        }
        catch (Exception e)
        {
            userId = 0;
        }

        if (userId == 0){
            isLogin = false;
        }

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(RecipeDetailActivity.this, AddOrEditRecipeActivity.class);
                intent1.putExtra("recipeId", Long.valueOf(recipeId));
                startActivity(intent1);
            }
        });

        Server server = new Server();
        Functions functions = new Functions(RecipeDetailActivity.this);
        functions.showLoading();
        server.getRecipeFromId(recipeId, userId).enqueue(new Callback<SingleRecipeResponse>() {
            @Override
            public void onResponse(Call<SingleRecipeResponse> call, Response<SingleRecipeResponse> response) {
                try {
                    if (!response.isSuccessful()){
                        functions.dismissLoading();
                        functions.showToast(String.valueOf(response.code()));
                        return;
                    }
                    if(!response.body().response.errorReason.equals("")){
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
                            if(singleRecipeResponse.recipe.creatorId == userId){
                                btn_edit.setVisibility(View.VISIBLE);
                            }
                            functions.dismissLoading();
                        }

                        @Override
                        public void onFailure(Call<UserResponse> call, Throwable t) {
                            functions.dismissLoading();
                            functions.showToast(t.getMessage());
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    functions.dismissLoading();
                }

            }

            @Override
            public void onFailure(Call<SingleRecipeResponse> call, Throwable t) {
                functions.dismissLoading();
                functions.showToast(t.getMessage());
            }
        });
    }
}