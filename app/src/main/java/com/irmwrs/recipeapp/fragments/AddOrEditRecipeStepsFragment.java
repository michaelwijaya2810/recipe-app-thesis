package com.irmwrs.recipeapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.irmwrs.recipeapp.Class.Step;
import com.irmwrs.recipeapp.Class.UpdateRecipe;
import com.irmwrs.recipeapp.MyItemTouchHelper;
import com.irmwrs.recipeapp.R;
import com.irmwrs.recipeapp.adapters.AddOrEditRecipeStepsAdapter;
import com.irmwrs.recipeapp.adapters.RecipeStepsAdapter;

import java.util.ArrayList;
import java.util.List;

public class AddOrEditRecipeStepsFragment extends Fragment implements AddOrEditRecipeStepsAdapter.sendData {
    List<Step> recipeSteps;
    getStepForm getData;
    Button btnSave;

    public AddOrEditRecipeStepsFragment(List<Step> recipeSteps, getStepForm getData, Button btnSave) {
        // Required empty public constructor
        this.recipeSteps = recipeSteps;
        this.getData = getData;
        this.btnSave = btnSave;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_or_edit_recipe_steps, container, false);
    }
    RecyclerView rvSteps;
    EditText etStepInstruction;
    Button btnAddSteps;
    List<Step> steps = new ArrayList<>();
    boolean isAddRecipe = true;
    AddOrEditRecipeStepsAdapter adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvSteps = view.findViewById(R.id.rvSteps);
        etStepInstruction = view.findViewById(R.id.etStepInstruction);
        btnAddSteps = view.findViewById(R.id.btnAddSteps);

        rvSteps.setLayoutManager(new LinearLayoutManager(this.getContext()));

        if (recipeSteps != null){ // Edit page
            steps = recipeSteps;
            isAddRecipe = false;
        }
        adapter = new AddOrEditRecipeStepsAdapter(steps, this);

        ItemTouchHelper.Callback callback = new MyItemTouchHelper(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        adapter.setTouchHelper(itemTouchHelper);
        itemTouchHelper.attachToRecyclerView(rvSteps);

        rvSteps.setAdapter(adapter);
        btnAddSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etStepInstruction.getText().toString().equals("")){
                    Toast.makeText(getContext(), "Step instruction can't be empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    addStep(etStepInstruction.getText().toString());
                    etStepInstruction.setText("");
                }
            }
        });
    }

    void addStep(String stepInstruction){
        Step step = new Step();
        step.recipeSteps = stepInstruction;
        if(!isAddRecipe){
            step.recipeId = steps.get(0).recipeId;
        }
        steps.add(step);
        adapter.notifyDataSetChanged();
        adapter.setRvIds();
    }

    @Override
    public void sendSteps(List<Step> steps) {
        this.steps = steps;
    }

    public interface getStepForm{
        void getStepData(List<Step> steps, boolean saveIsPressed);
    }

    @Override
    public void onStart() {
        super.onStart();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData.getStepData(steps, true);
            }
        });
        getData.getStepData(steps, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData.getStepData(steps, true);
            }
        });
        getData.getStepData(steps, false);
    }

    @Override
    public void onPause() {
        super.onPause();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData.getStepData(steps, true);
            }
        });
        getData.getStepData(steps, false);
    }

}