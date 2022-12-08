package com.irmwrs.recipeapp;

import androidx.appcompat.app.AppCompatActivity;

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

        ImageView ivCancelButton = findViewById(R.id.ivCancelButton);
        RatingBar rbRecipe = findViewById(R.id.rbRecipe);
        EditText etReview = findViewById(R.id.etReview);
        Button btnDone = findViewById(R.id.btnDone);

        ivCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // pop activity
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

                    Gson gson = new Gson();
                    String value = gson.toJson(review);
                    List<Key> keys = new ArrayList<>();
                    Key key = new Key();
                    key.value = value;
                    keys.add(key);

                    Log.i("testRecipe", value);

                    Server server = new Server();
                    server.getAuthToken(userId, keys).enqueue(new Callback<Key>() {
                        @Override
                        public void onResponse(Call<Key> call, Response<Key> response) {
                            if (!response.isSuccessful()){
                                Log.i("testRecipe", response.toString());
                                Toast.makeText(getApplicationContext(), "1" + String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                                return;
                            }
                            String auth = response.body().value;
                            server.postRating(recipeId, userId, auth, review).enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    if (!response.isSuccessful()){
                                        Log.i("testRecipe", response.toString());
                                        Toast.makeText(getApplicationContext(), "1" + String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Log.i("testRecipe", "rating : " + t.getMessage());
                                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onFailure(Call<Key> call, Throwable t) {
                            Log.i("testRecipe", "rating : " + t.getMessage());
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
//                    server.getAuthToken(userId, keys).enqueue(new Callback<Key>() {
//                        @Override
//                        public void onResponse(Call<String> call, Response<String> response) {
//                            if (!response.isSuccessful()){
//                                Log.i("testRecipe", response.toString());
//                                Toast.makeText(getApplicationContext(), "1" + String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
//                                return;
//                            }
//                            String authToken = response.body();
//                            Log.i("testRecipe", "auth: " + authToken);
//                            server.postRating(recipeId, userId, authToken, review).enqueue(new Callback<String>() {
//                                @Override
//                                public void onResponse(Call<String> call, Response<String> response) {
//                                    if (!response.isSuccessful()){
//                                        Toast.makeText(getApplicationContext(), String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
//                                        return;
//                                    }
//                                    Toast.makeText(getApplicationContext(), "Thank you for your feedback!", Toast.LENGTH_SHORT).show();
//                                    // navigate to wherever
//                                }
//
//                                @Override
//                                public void onFailure(Call<String> call, Throwable t) {
//                                    Log.i("testRecipe", "rating : " + t.getMessage());
//                                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        }
//
//                        @Override
//                        public void onFailure(Call<String> call, Throwable t) {
//                            Log.i("testRecipe", "auth: " + t.getMessage());
//                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
                }
                else {
                    // navigate to wherever
                }
            }
        });
    }
}