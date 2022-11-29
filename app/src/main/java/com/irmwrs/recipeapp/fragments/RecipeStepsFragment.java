package com.irmwrs.recipeapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.irmwrs.recipeapp.Class.Step;
import com.irmwrs.recipeapp.R;
import com.irmwrs.recipeapp.adapters.RecipeStepsAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecipeStepsFragment extends Fragment {

    private RecyclerView recipeStepsList;
    private Button btnCompleteRecipe;
    List<Step> steps;

    public RecipeStepsFragment(List<Step> steps) {
        // Required empty public constructor
        this.steps = steps;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_steps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recipeStepsList = view.findViewById(R.id.recipe_steps_list);
        btnCompleteRecipe = view.findViewById(R.id.btn_complete_recipe);

        recipeStepsList.setLayoutManager(new LinearLayoutManager(this.getContext()));
        RecipeStepsAdapter adapter = new RecipeStepsAdapter(steps);
        recipeStepsList.setAdapter(adapter);

        btnCompleteRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Hooray! Recipe Completed!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}