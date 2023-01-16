package com.irmwrs.recipeapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.irmwrs.recipeapp.Class.Step;
import com.irmwrs.recipeapp.OnAllStepsChecked;
import com.irmwrs.recipeapp.R;
import com.irmwrs.recipeapp.viewholders.RecipeStepViewHolder;

import java.util.List;

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepViewHolder>{
    static int checkCount = 0;
    OnAllStepsChecked listener;
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
        if (holder.checkBoxStep.isChecked()) {
            checkCount++;
        } else {
            checkCount--;
            if (checkCount < 0)
                checkCount = 0;
        }
        listener.onAllChecked(checkCount);
        holder.checkBoxStep.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (holder.checkBoxStep.isChecked()) {
                    checkCount++;
                } else {
                    checkCount--;
                    if (checkCount < 0)
                        checkCount = 0;
                }
                listener.onAllChecked(checkCount);
            }
        });
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    List<Step> steps;

    public RecipeStepsAdapter(List<Step> steps, OnAllStepsChecked listener){
        this.steps = steps;
        this.listener = listener;
    }
}
