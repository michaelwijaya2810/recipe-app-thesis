package com.irmwrs.recipeapp.viewholders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.irmwrs.recipeapp.R;

public class RecipeStepViewHolder extends RecyclerView.ViewHolder{
    public MaterialCheckBox checkBoxStep;

    public RecipeStepViewHolder(@NonNull View itemView) {
        super(itemView);
        checkBoxStep = itemView.findViewById(R.id.checkbox_step);
    }
}
