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
    @NonNull
    @Override
    public RecipeStepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe_step, parent, false);
        return new RecipeStepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeStepViewHolder holder, int position) {
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
    SparseBooleanArray checkedStatus = new SparseBooleanArray();
    OnStepsCheckedChangeListener listener;

    public RecipeStepsAdapter(List<Step> steps, OnStepsCheckedChangeListener listener){
        this.steps = steps;
        this.listener = listener;
    }

    class RecipeStepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public MaterialCheckBox checkBoxStep;

        public RecipeStepViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBoxStep = itemView.findViewById(R.id.checkbox_step);
            itemView.setOnClickListener(this);
        }

        void bind(int position) {
            if (!checkedStatus.get(position, false)) {
                checkBoxStep.setChecked(false);
            } else {
                checkBoxStep.setChecked(true);
            }
            checkBoxStep.setText(steps.get(position).recipeSteps);
            checkBoxStep.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
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
