package com.irmwrs.recipeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;
import com.irmwrs.recipeapp.Class.Ingredient;
import com.irmwrs.recipeapp.Class.Key;
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
    int creatorId;
    List<Step> steps;
    Functions functions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context;
        SharedPreferences sharepref;
        context = getApplicationContext();
        sharepref = context.getSharedPreferences("userinfo",Context.MODE_PRIVATE);

        creatorId = sharepref.getInt("Userid",0);
        if(creatorId == 0)
        {
            Toast.makeText(context, "Invalid Login Session", Toast.LENGTH_SHORT).show();

            Intent intentlogin = new Intent(context, Login.class);
            startActivity(intentlogin);

        }
        setContentView(R.layout.activity_add_or_edit_recipe);

        functions = new Functions(AddOrEditRecipeActivity.this);

        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        Intent intent = getIntent();
        recipeId = intent.getLongExtra("recipeId", 0);
        //creatorId = intent.getStringExtra("creatorId");
        // get creatorId from savedInstance or get it from recipeId

        // sample data
        //creatorId = "8";



//        recipeId = 10;

        Server server = new Server();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if(recipeId == 0){ // Add page
            steps = new ArrayList<>();
            functions.showLoading();
            server.getAllIngredient().enqueue(new Callback<List<Ingredient>>() {
                @Override
                public void onResponse(Call<List<Ingredient>> call, Response<List<Ingredient>> response) {
                    if (!response.isSuccessful()){
                        functions.dismissLoading();
                        functions.showToast(String.valueOf(response.code()));
                        return;
                    }
                    functions.dismissLoading();
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
                    functions.dismissLoading();
                    functions.showToast(t.getMessage());
                }
            });
        }
        else { // Edit page
            functions.showLoading();
            server.getRecipeFromId(recipeId, creatorId).enqueue(new Callback<SingleRecipeResponse>() {
                @Override
                public void onResponse(Call<SingleRecipeResponse> call, Response<SingleRecipeResponse> response) {
                    if (!response.isSuccessful()){
                        functions.dismissLoading();
                        functions.showToast(String.valueOf(response.code()));
                        return;
                    }
                    if(!response.body().response.errorReason.equals("")){
                        functions.dismissLoading();
                        functions.showToast(String.valueOf(response.body().response.errorReason));
                        return;
                    }
                    singleRecipeResponse = response.body();
                    steps = singleRecipeResponse.steps;
                    server.getAllIngredient().enqueue(new Callback<List<Ingredient>>() {
                        @Override
                        public void onResponse(Call<List<Ingredient>> call, Response<List<Ingredient>> response) {
                            if (!response.isSuccessful()){
                                functions.dismissLoading();
                                functions.showToast(String.valueOf(response.code()));
                                return;
                            }
                            functions.dismissLoading();
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

    UpdateRecipe updateRecipe;
    Gson gson = new Gson();
    boolean isValid;

    @Override
    public void getIngredientData(UpdateRecipe updateRecipe, boolean saveIsPressed) {
        if(saveIsPressed){
            this.updateRecipe = updateRecipe;
            this.updateRecipe.stepList = steps;
            this.updateRecipe.recipeId = recipeId;
            this.updateRecipe.creatorId =creatorId;
            isValid = validate(updateRecipe);
            if(isValid){
                functions.showToast("Sending data...");
                sendData();
            }
            Log.i("recipeTest", gson.toJson(updateRecipe));
        }
        else {
            this.updateRecipe = updateRecipe;
        }
    }

    @Override
    public void getStepData(List<Step> steps, boolean saveIsPressed) {
        if(saveIsPressed){
            updateRecipe.stepList = steps;
            updateRecipe.recipeId = recipeId;
            updateRecipe.creatorId = creatorId;
            isValid = validate(this.updateRecipe);
            if(isValid){
                functions.showToast("Sending data...");
                sendData();
            }
            Log.i("recipeTest", gson.toJson(updateRecipe.stepList));
        }
        else {
            this.steps = steps;
        }
    }



    String imageRecipe = "";

    void sendData(){
        imageRecipe = updateRecipe.recipeImage;
        updateRecipe.recipeImage = "";
        for (int i = 0; i < updateRecipe.stepList.size(); i++){
            updateRecipe.stepList.get(i).rvId = null;
        }
        Server server = new Server();
        functions.showLoading();
        server.getAuthToken(creatorId, updateRecipe).enqueue(new Callback<Key>() {
            @Override
            public void onResponse(Call<Key> call, Response<Key> response) {
                if (!response.isSuccessful()){
                    functions.dismissLoading();
                    functions.showToast(String.valueOf(response.code()));
                    return;
                }
                String auth = response.body().value;
                updateRecipe.recipeImage = imageRecipe;
                server.postCreateOrUpdateRecipe(updateRecipe, auth).enqueue(new Callback<com.irmwrs.recipeapp.Class.ResponseClass.Response>() {
                    @Override
                    public void onResponse(Call<com.irmwrs.recipeapp.Class.ResponseClass.Response> call, Response<com.irmwrs.recipeapp.Class.ResponseClass.Response> response) {
                        if (!response.isSuccessful()){
                            functions.dismissLoading();
                            functions.showToast(String.valueOf(response.code()));
                            return;
                        }
                        if(!response.body().errorReason.equals("")){
                            functions.dismissLoading();
                            functions.showToast(response.body().errorReason);
                            return;
                        }
                        functions.dismissLoading();
                        if(updateRecipe.recipeId == 0){
                            functions.showLongToast("Recipe submitted, your recipe will be available after admin approval!");
                        }
                        else {
                            functions.showToast("Recipe edited");
                        }
                        Intent intent = new Intent(AddOrEditRecipeActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<com.irmwrs.recipeapp.Class.ResponseClass.Response> call, Throwable t) {
                        functions.dismissLoading();
                        functions.showToast(t.getMessage());
                    }
                });
            }

            @Override
            public void onFailure(Call<Key> call, Throwable t) {
                functions.dismissLoading();
                functions.showToast(t.getMessage());
            }
        });
    }

    boolean validate(UpdateRecipe updateRecipe){
        if(updateRecipe.recipeImage.equals("")){
            functions.showToast("Recipe image can't be empty");
            return false;
        }
        else if(updateRecipe.recipeName.equals("")){
            functions.showToast("Recipe name can't be empty");
            return false;
        }
        else if(updateRecipe.recipeDifficulty == 0){
            functions.showToast("Please pick a difficulty");
            return false;
        }
        else if(updateRecipe.recipeDescription.equals("")){
            functions.showToast("Recipe description can't be empty");
            return false;
        }
        else if(updateRecipe.ingredientList.size() == 0){
            functions.showToast("Please add at least one ingredient");
            return false;
        }
        else if(updateRecipe.stepList.size() == 0){
            functions.showToast("Please add at least one step");
            return false;
        }
        return true;
    }
}