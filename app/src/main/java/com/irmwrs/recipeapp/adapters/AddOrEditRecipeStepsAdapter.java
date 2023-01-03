package com.irmwrs.recipeapp.adapters;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.irmwrs.recipeapp.Class.Step;
import com.irmwrs.recipeapp.R;
import com.irmwrs.recipeapp.viewholders.AddOrEditRecipeStepViewHolder;

import java.util.List;

public class AddOrEditRecipeStepsAdapter extends RecyclerView.Adapter<AddOrEditRecipeStepViewHolder> implements ItemTouchHelperAdapter{
    @NonNull
    @Override
    public AddOrEditRecipeStepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_or_edit_recipe_step, parent, false);
        return new AddOrEditRecipeStepViewHolder(view, itemTouchHelper);
    }

    @Override
    public void onBindViewHolder(@NonNull AddOrEditRecipeStepViewHolder holder, int position) {
        Step step = steps.get(position);
        holder.etSteps.setText(step.recipeSteps);
        holder.etSteps.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() != 0){
                    for (int i = 0; i < steps.size(); i++){
                        if(steps.get(i).rvId.equals(step.rvId)){
                            steps.get(i).recipeSteps = editable.toString();
                            data.sendSteps(steps);
                        }
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    List<Step> steps;
    ItemTouchHelper itemTouchHelper;
    sendData data;

    public AddOrEditRecipeStepsAdapter(List<Step> steps, sendData data){
        this.steps = steps;
        this.data = data;
        for (int i = 0; i < steps.size(); i++){ // set rvId
            steps.get(i).rvId = String.valueOf(i);
        }
    }

    public void setRvIds(){
        for (int i = 0; i < steps.size(); i++){ // set rvId
            steps.get(i).rvId = String.valueOf(i);
        }
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Step fromStep = steps.get(fromPosition);
        steps.remove(fromStep);
        steps.add(toPosition, fromStep);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemSwiped(int position) {
        steps.remove(position);
        notifyItemRemoved(position);

    }

    public void setTouchHelper(ItemTouchHelper itemTouchHelper){
        this.itemTouchHelper = itemTouchHelper;
    }

    public interface sendData{
        void sendSteps(List<Step> steps);
    }
}
