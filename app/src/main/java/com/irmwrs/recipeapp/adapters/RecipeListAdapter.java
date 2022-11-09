package com.irmwrs.recipeapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.irmwrs.recipeapp.R;
import com.irmwrs.recipeapp.viewholders.RecipeViewHolder;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeViewHolder> {

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe_viewholder, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.recipeImage.setImageResource(R.drawable.no_image_placeholder);
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
