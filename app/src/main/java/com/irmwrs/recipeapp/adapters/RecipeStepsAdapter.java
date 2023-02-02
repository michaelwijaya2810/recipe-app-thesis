package com.irmwrs.recipeapp.adapters;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.irmwrs.recipeapp.Class.Step;
import com.irmwrs.recipeapp.OnStepsCheckedChangeListener;
import com.irmwrs.recipeapp.R;

import java.util.List;

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.RecipeStepViewHolder>{
    SparseBooleanArray checkedStatus = new SparseBooleanArray();
    OnStepsCheckedChangeListener listener;
    @NonNull
    @Override
    public RecipeStepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe_step, parent, false);
        return new RecipeStepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeStepViewHolder holder, int position) {
        Step step = steps.get(holder.getAdapterPosition());
        holder.checkBoxStep.setText(step.recipeSteps);
        holder.bind(holder.getAdapterPosition());
    }

    @Override
    public int getItemCount() {
        if (steps == null) {
            return 0;
        }
        return steps.size();
    }

    List<Step> steps;

    public RecipeStepsAdapter(List<Step> steps, OnStepsCheckedChangeListener listener){
        this.steps = steps;
        this.listener = listener;
    }

    class RecipeStepViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener{
        public MaterialCheckBox checkBoxStep;

        public RecipeStepViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBoxStep = itemView.findViewById(R.id.checkbox_step);
        }

        public void bind(int position) {
            if (!checkedStatus.get(position, false)) {
                checkBoxStep.setChecked(false);
            } else {
                checkBoxStep.setChecked(true);
            }
            checkBoxStep.setOnCheckedChangeListener(this);
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            int adapterPosition = getAdapterPosition();
            if (!checkedStatus.get(adapterPosition, false)) {
                checkBoxStep.setChecked(true);
                checkedStatus.put(adapterPosition, true);
            }
            else  {
                checkBoxStep.setChecked(false);
                checkedStatus.put(adapterPosition, false);
            }
            listener.onCheckedChange(adapterPosition, checkBoxStep.isChecked());
        }
    }
}
