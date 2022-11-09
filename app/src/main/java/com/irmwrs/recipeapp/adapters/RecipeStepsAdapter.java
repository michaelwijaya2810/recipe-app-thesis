package com.irmwrs.recipeapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.irmwrs.recipeapp.R;
import com.irmwrs.recipeapp.viewholders.RecipeStepViewHolder;

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepViewHolder>{
    @NonNull
    @Override
    public RecipeStepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe_step, parent, false);
        return new RecipeStepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeStepViewHolder holder, int position) {
        holder.checkBoxStep.setText("Recipe Step Value");
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
