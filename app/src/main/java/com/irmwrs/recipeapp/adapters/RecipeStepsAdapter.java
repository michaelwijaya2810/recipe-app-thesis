package com.irmwrs.recipeapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.irmwrs.recipeapp.Class.Step;
import com.irmwrs.recipeapp.R;
import com.irmwrs.recipeapp.viewholders.RecipeStepViewHolder;

import java.util.List;

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepViewHolder>{
    @NonNull
    @Override
    public RecipeStepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe_step, parent, false);
        return new RecipeStepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeStepViewHolder holder, int position) {
        Step step = steps.get(position);
        holder.checkBoxStep.setText(step.recipeSteps);
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    List<Step> steps;

    public RecipeStepsAdapter(List<Step> steps){
        this.steps = steps;
    }
}
