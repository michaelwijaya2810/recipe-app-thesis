package com.irmwrs.recipeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;
import com.irmwrs.recipeapp.Class.Ingredient;
import com.irmwrs.recipeapp.Class.ResponseClass.SingleRecipeResponse;
import com.irmwrs.recipeapp.Class.Step;
import com.irmwrs.recipeapp.Class.UpdateRecipe;
import com.irmwrs.recipeapp.adapters.AddOrEditRecipeAdapter;
import com.irmwrs.recipeapp.fragments.AddOrEditRecipeIngredientsFragment;
import com.irmwrs.recipeapp.fragments.AddOrEditRecipeStepsFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddOrEditRecipeActivity extends AppCompatActivity implements AddOrEditRecipeIngredientsFragment.getIngredientForm, AddOrEditRecipeStepsFragment.getStepForm {

    TabLayout tabLayout;
    TabLayoutMediator mediator;
    ViewPager2 recipeAddOrEditPager;
    AddOrEditRecipeAdapter recipeAddOrEditAdapter;
    SingleRecipeResponse singleRecipeResponse;
    Button btnSave;
    Button btnCancel;
    long recipeId;
    String creatorId;
    List<Step> steps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit_recipe);

        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        Intent intent = getIntent();
        recipeId = intent.getLongExtra("recipeId", -1);
        creatorId = intent.getStringExtra("creatorId");

        Server server = new Server();

        if(recipeId == -1){ // Add page
            steps = new ArrayList<>();
            server.getAllIngredient().enqueue(new Callback<List<Ingredient>>() {
                @Override
                public void onResponse(Call<List<Ingredient>> call, Response<List<Ingredient>> response) {
                    if (!response.isSuccessful()){
                        Toast.makeText(getApplicationContext(), String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    tabLayout = findViewById(R.id.tlAddOrEdit);
                    recipeAddOrEditPager = findViewById(R.id.vpAddOrEdit);
                    recipeAddOrEditAdapter = new AddOrEditRecipeAdapter(getSupportFragmentManager(), getLifecycle(), response.body(), null, btnSave, AddOrEditRecipeActivity.this, AddOrEditRecipeActivity.this);
                    recipeAddOrEditPager.setAdapter(recipeAddOrEditAdapter);
                    mediator = new TabLayoutMediator(tabLayout, recipeAddOrEditPager,
                            (tab, position) -> {
                                if (position == 0) {
                                    tab.setText("Ingredients");
                                } else if (position == 1) {
                                    tab.setText("Steps");
                                }
                            });
                    mediator.attach();
                }

                @Override
                public void onFailure(Call<List<Ingredient>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else { // Edit page
            server.getRecipeFromId(recipeId).enqueue(new Callback<SingleRecipeResponse>() {
                @Override
                public void onResponse(Call<SingleRecipeResponse> call, Response<SingleRecipeResponse> response) {
                    if (!response.isSuccessful()){
                        Toast.makeText(getApplicationContext(), String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    singleRecipeResponse = response.body();
                    steps = singleRecipeResponse.steps;
                    server.getAllIngredient().enqueue(new Callback<List<Ingredient>>() {
                        @Override
                        public void onResponse(Call<List<Ingredient>> call, Response<List<Ingredient>> response) {
                            if (!response.isSuccessful()){
                                Toast.makeText(getApplicationContext(), String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                                return;
                            }
                            tabLayout = findViewById(R.id.tlAddOrEdit);
                            recipeAddOrEditPager = findViewById(R.id.vpAddOrEdit);
                            recipeAddOrEditAdapter = new AddOrEditRecipeAdapter(getSupportFragmentManager(), getLifecycle(), response.body(), singleRecipeResponse, btnSave, AddOrEditRecipeActivity.this, AddOrEditRecipeActivity.this);
                            recipeAddOrEditPager.setAdapter(recipeAddOrEditAdapter);
                            mediator = new TabLayoutMediator(tabLayout, recipeAddOrEditPager,
                                    (tab, position) -> {
                                        if (position == 0) {
                                            tab.setText("Ingredients");
                                        } else if (position == 1) {
                                            tab.setText("Steps");
                                        }
                                    });
                            mediator.attach();
                        }

                        @Override
                        public void onFailure(Call<List<Ingredient>> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onFailure(Call<SingleRecipeResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    UpdateRecipe updateRecipe;
    Gson gson = new Gson();
    boolean isValid;

    @Override
    public void getIngredientData(UpdateRecipe updateRecipe, boolean saveIsPressed) {
        if(saveIsPressed){
            this.updateRecipe = updateRecipe;
            this.updateRecipe.stepList = steps;
            if (recipeId != -1){
                updateRecipe.recipeId = recipeId;
                updateRecipe.creatorId = creatorId;
            }
            isValid = validate(updateRecipe);
            if(isValid){
                showErrorToast("Sending data...");
            }
            Log.i("recipeTest", gson.toJson(updateRecipe));
            // call server
        }
        else {
            this.updateRecipe = updateRecipe;
        }
    }

    @Override
    public void getStepData(List<Step> steps, boolean saveIsPressed) {
        if(saveIsPressed){
            this.updateRecipe.stepList = steps;
            if (recipeId != -1){
                updateRecipe.recipeId = recipeId;
                updateRecipe.creatorId = creatorId;
            }
            isValid = validate(this.updateRecipe);
            if(isValid){
                showErrorToast("Sending data...");
            }
            Log.i("recipeTest", gson.toJson(updateRecipe));
            // call server
        }
        else {
            this.steps = steps;
        }
    }

    boolean validate(UpdateRecipe updateRecipe){
        if(updateRecipe.recipeImage.equals("")){
            showErrorToast("Recipe image can't be empty");
            return false;
        }
        else if(updateRecipe.recipeName.equals("")){
            showErrorToast("Recipe name can't be empty");
            return false;
        }
        else if(updateRecipe.recipeDifficulty == 0){
            showErrorToast("Please pick a difficulty");
            return false;
        }
        else if(updateRecipe.recipeDescription.equals("")){
            showErrorToast("Recipe description can't be empty");
            return false;
        }
        else if(updateRecipe.ingredientList.size() == 0){
            showErrorToast("Please add at least one ingredient");
            return false;
        }
        else if(updateRecipe.stepList.size() == 0){
            showErrorToast("Please add at least one step");
            return false;
        }
        return true;
    }

    void showErrorToast(String errorMsg){
        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }
}