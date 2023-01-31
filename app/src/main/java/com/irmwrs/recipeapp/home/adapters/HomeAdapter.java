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
import com.irmwrs.recipeapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    Context ctx;
    List<Recipe> recipes;
    ViewHolder.OnRecipeListener onRecipeListener;

    public HomeAdapter(Context ctx, List<Recipe> recipes, ViewHolder.OnRecipeListener onRecipeListener){
        this.ctx = ctx;
        this.recipes = recipes;
        this.onRecipeListener = onRecipeListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.item_recipe_fixed_width_viewholder, parent, false);
        return new HomeAdapter.ViewHolder(v, onRecipeListener, recipes);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView recipeImage;
        TextView recipeTitle;
        TextView recipeRating;
        OnRecipeListener onRecipeListener;
        List<Recipe> recipes;

        public ViewHolder(@NonNull View itemView, OnRecipeListener onRecipeListener, List<Recipe> recipes) {
            super(itemView);
            recipeImage = itemView.findViewById(R.id.recipe_image);
            recipeTitle = itemView.findViewById(R.id.recipe_title);
            recipeRating = itemView.findViewById(R.id.recipe_rating);

            this.onRecipeListener = onRecipeListener;
            itemView.setOnClickListener(this);

            this.recipes = recipes;
        }

        @Override
        public void onClick(View view) {
            onRecipeListener.onRecipeClick(recipes.get(getAdapterPosition()).id);
        }

        public interface OnRecipeListener {
            void onRecipeClick(int id);
        }

    }
}
