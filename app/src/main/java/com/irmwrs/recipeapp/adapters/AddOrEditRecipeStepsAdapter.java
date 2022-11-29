package com.irmwrs.recipeapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.irmwrs.recipeapp.Class.Step;
import com.irmwrs.recipeapp.R;
import com.irmwrs.recipeapp.viewholders.AddOrEditRecipeStepViewHolder;

import java.util.List;

public class AddOrEditRecipeStepsAdapter extends RecyclerView.Adapter<AddOrEditRecipeStepViewHolder>{
    @NonNull
    @Override
    public AddOrEditRecipeStepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_or_edit_recipe_step, parent, false);
        return new AddOrEditRecipeStepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddOrEditRecipeStepViewHolder holder, int position) {
        Step step = steps.get(position);
        holder.etSteps.setText(step.recipeSteps);
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    List<Step> steps;
    boolean isAddRecipe = false;

    public AddOrEditRecipeStepsAdapter(List<Step> steps){
        this.steps = steps;
        if(steps.size() == 0){
            isAddRecipe = true;
        }
    }

    public void addSteps(String stepInstruction){
        Step step = new Step();
        step.recipeSteps = stepInstruction;
        if(!isAddRecipe){
            step.recipeId = steps.get(0).recipeId;
        }
        steps.add(step);
        notifyDataSetChanged();
    }
}
