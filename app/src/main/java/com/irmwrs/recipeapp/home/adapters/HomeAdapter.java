package com.irmwrs.recipeapp.home.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.irmwrs.recipeapp.Class.Recipe;
import com.irmwrs.recipeapp.OnRecipeListener;
import com.irmwrs.recipeapp.R;
import com.irmwrs.recipeapp.viewholders.HomeViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeViewHolder> {

    Context ctx;
    List<Recipe> recipes;
    OnRecipeListener onRecipeListener;

    public HomeAdapter(Context ctx, List<Recipe> recipes, OnRecipeListener onRecipeListener){
        this.ctx = ctx;
        this.recipes = recipes;
        this.onRecipeListener = onRecipeListener;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.item_recipe_fixed_width_viewholder, parent, false);
        return new HomeViewHolder(v, onRecipeListener, recipes);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.recipeTitle.setText(recipe.recipeName);
        if(recipe.recipeImage.equals("")){
            holder.recipeImage.setImageResource(R.drawable.no_image_placeholder);
        }
        else {
            Picasso.get().load(recipes.get(position).recipeImage).into(holder.recipeImage);
//            byte[] decodedBytes = Base64.decode(recipe.recipeImage, Base64.DEFAULT);
//            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
//            holder.recipeImage.setImageBitmap(bitmap);
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
}
