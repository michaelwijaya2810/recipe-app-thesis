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
import com.irmwrs.recipeapp.MainActivity;
import com.irmwrs.recipeapp.OnRecipeListener;
import com.irmwrs.recipeapp.R;
import com.irmwrs.recipeapp.viewholders.RecipeViewHolder;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
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
        Recipe recipe = recipes.get(holder.getAdapterPosition());
        holder.recipeTitle.setText(recipe.recipeName);

        Picasso.get().load(recipes.get(holder.getAdapterPosition()).recipeImage).into(holder.recipeImage);
        String rating;
        if(recipe.recipeRating.equals("0")){
            rating = "No rating yet";
        }
        else {
            Double ratingDecimal = Double.parseDouble(recipe.recipeRating);
            DecimalFormat df = new DecimalFormat("#.#");
            String finalRating = df.format(ratingDecimal);
            rating = finalRating + " / 5";
        }
        holder.recipeRating.setText(rating);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    List<Recipe> recipes;
    OnRecipeListener onRecipeListener;

    public RecipeListAdapter(List<Recipe> recipes, OnRecipeListener onRecipeListener){
        this.onRecipeListener = onRecipeListener;
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    public void updateList(List<Recipe> recipes){
        this.recipes = recipes;
        notifyDataSetChanged();
    }
}
