package com.irmwrs.recipeapp.viewholders;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.irmwrs.recipeapp.R;
import com.irmwrs.recipeapp.adapters.AddOrEditRecipeStepsAdapter;

public class AddOrEditRecipeStepViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener, GestureDetector.OnGestureListener {
    public EditText etSteps;

    GestureDetector gestureDetector;
    ItemTouchHelper itemTouchHelper;

    public AddOrEditRecipeStepsAdapter.AddOrEditRecipeStepsEditTextListener recipeStepsEditTextListener;

    public AddOrEditRecipeStepViewHolder(@NonNull View itemView,
                                            ItemTouchHelper itemTouchHelper,
                                            AddOrEditRecipeStepsAdapter.AddOrEditRecipeStepsEditTextListener recipeStepsEditTextListener) {
        super(itemView);
        etSteps = itemView.findViewById(R.id.etSteps);

        gestureDetector = new GestureDetector(itemView.getContext(), this);
        this.itemTouchHelper = itemTouchHelper;
        this.recipeStepsEditTextListener = recipeStepsEditTextListener;
        itemView.setOnTouchListener(this);
        etSteps.addTextChangedListener(recipeStepsEditTextListener);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        itemTouchHelper.startDrag(this);
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        gestureDetector.onTouchEvent(motionEvent);
        return true;
    }
}
