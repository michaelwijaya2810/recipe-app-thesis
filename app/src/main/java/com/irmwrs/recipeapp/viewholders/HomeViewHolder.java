package com.irmwrs.recipeapp.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.irmwrs.recipeapp.Class.Recipe;
import com.irmwrs.recipeapp.OnRecipeListener;
import com.irmwrs.recipeapp.R;

import java.util.List;

public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ImageView recipeImage;
    public TextView recipeTitle;
    public TextView recipeRating;
    OnRecipeListener onRecipeListener;
    List<Recipe> recipes;

    public HomeViewHolder(@NonNull View itemView, OnRecipeListener onRecipeListener, List<Recipe> recipes) {
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
}
