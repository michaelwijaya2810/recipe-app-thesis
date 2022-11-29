package com.irmwrs.recipeapp.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.irmwrs.recipeapp.Class.Recipe;
import com.irmwrs.recipeapp.R;
import com.irmwrs.recipeapp.viewholders.RecipeViewHolder;

import java.util.BitSet;
import java.util.List;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeViewHolder> {

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe_viewholder, parent, false);
        return new RecipeViewHolder(view, onRecipeListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.recipeTitle.setText(recipe.recipeName);
        if(recipe.recipeImage == ""){
            holder.recipeImage.setImageResource(R.drawable.no_image_placeholder);
        }
        else {
            byte[] decodedBytes = Base64.decode(recipe.recipeImage, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
            holder.recipeImage.setImageBitmap(bitmap);
        }
        String rating;
        if(recipe.recipeRating.equals("0")){
            rating = "No rating yet";
        }
        else {
            rating = recipe.recipeRating + " / 5";
        }
        holder.recipeRating.setText(rating);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    List<Recipe> recipes;
    RecipeViewHolder.OnRecipeListener onRecipeListener;

    public RecipeListAdapter(List<Recipe> recipes, RecipeViewHolder.OnRecipeListener onRecipeListener){
        this.onRecipeListener = onRecipeListener;
        this.recipes = recipes;
        Recipe recipe = new Recipe();
        recipe.recipeImage = "";
        recipe.recipeName = "test Recipe";
        recipe.recipeRating = "3";
        recipes.add(recipe);
        recipes.add(recipe);
        recipes.add(recipe);
        notifyDataSetChanged();
    }
}
