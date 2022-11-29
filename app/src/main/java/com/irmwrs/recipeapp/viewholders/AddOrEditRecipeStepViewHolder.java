package com.irmwrs.recipeapp.viewholders;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.irmwrs.recipeapp.R;

public class AddOrEditRecipeStepViewHolder extends RecyclerView.ViewHolder{
    public EditText etSteps;

    public AddOrEditRecipeStepViewHolder(@NonNull View itemView) {
        super(itemView);
        etSteps = itemView.findViewById(R.id.etSteps);
    }
}
