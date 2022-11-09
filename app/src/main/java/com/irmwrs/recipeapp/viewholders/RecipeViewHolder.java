package com.irmwrs.recipeapp.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.irmwrs.recipeapp.R;

public class RecipeViewHolder extends RecyclerView.ViewHolder {
    public MaterialCardView recipeCard;
    public ImageView recipeImage;
    public TextView recipeTitle;
    public ImageView recipeRatingIcon;
    public TextView recipeRating;

    public RecipeViewHolder(@NonNull View itemView) {
        super(itemView);
        recipeCard = itemView.findViewById(R.id.recipe_card);
        recipeImage = itemView.findViewById(R.id.recipe_image);
        recipeTitle = itemView.findViewById(R.id.recipe_title);
        recipeRatingIcon = itemView.findViewById(R.id.recipe_rating_icon);
        recipeRating = itemView.findViewById(R.id.recipe_rating);
    }
}
