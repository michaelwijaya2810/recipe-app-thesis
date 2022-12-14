package com.irmwrs.recipeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.irmwrs.recipeapp.Class.Key;
import com.irmwrs.recipeapp.Class.Review;

import java.time.chrono.JapaneseChronology;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RatingActivity extends AppCompatActivity {
    boolean isRated = false;
    int userId = 8; // get user id
    long recipeId = 2; // get recipe id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", 0);
        recipeId = intent.getIntExtra("recipeId", 0);
        Log.i("testRecipe", "user " + userId + " recipe " + recipeId);

        ImageView ivCancelButton = findViewById(R.id.ivCancelButton);
        RatingBar rbRecipe = findViewById(R.id.rbRecipe);
        EditText etReview = findViewById(R.id.etReview);
        Button btnDone = findViewById(R.id.btnDone);

        ivCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        rbRecipe.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                etReview.setVisibility(View.VISIBLE);
                isRated = true;
                if(v < 1){ // rating minimum 1
                    ratingBar.setRating(1);
                }
            }
        });
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRated){
                    Review review = new Review();
                    review.recipeReview = etReview.getText().toString();
                    review.reviewerId = userId;
                    review.recipeRating = (int) rbRecipe.getRating();
                    review.recipesId = recipeId;

                    Server server = new Server();
                    server.getAuthToken(userId, review).enqueue(new Callback<Key>() {
                        @Override
                        public void onResponse(Call<Key> call, Response<Key> response) {
                            if (!response.isSuccessful()){
                                Toast.makeText(getApplicationContext(), String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                                return;
                            }
                            String auth = response.body().value;
                            Log.i("testRecipe", auth);

                            server.postRating(recipeId, userId, auth, review).enqueue(new Callback<com.irmwrs.recipeapp.Class.ResponseClass.Response>() {
                                @Override
                                public void onResponse(Call<com.irmwrs.recipeapp.Class.ResponseClass.Response> call, Response<com.irmwrs.recipeapp.Class.ResponseClass.Response> response) {
                                    if (!response.isSuccessful()){
                                        Toast.makeText(getApplicationContext(), String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    if(response.body().errorReason != null){
                                        Toast.makeText(getApplicationContext(), response.body().errorReason, Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    Toast.makeText(getApplicationContext(), "Thank you for your feedback!", Toast.LENGTH_SHORT).show();
                                    Intent intent1 = new Intent(RatingActivity.this, MainActivity.class);
                                    intent1.putExtra("selectedTab", 3);
                                    startActivity(intent1);
                                }

                                @Override
                                public void onFailure(Call<com.irmwrs.recipeapp.Class.ResponseClass.Response> call, Throwable t) {
                                    Log.i("testRecipe", "rating");
                                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onFailure(Call<Key> call, Throwable t) {
                            Log.i("testRecipe", "auth");
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    Intent intent1 = new Intent(RatingActivity.this, MainActivity.class);
                    intent1.putExtra("selectedTab", 3);
                    startActivity(intent1);
                    // navigate to wherever
                }
            }
        });
    }
}