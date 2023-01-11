package com.irmwrs.recipeapp.fragments;

import android.content.Intent;
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
import com.irmwrs.recipeapp.MainActivity;
import com.irmwrs.recipeapp.R;
import com.irmwrs.recipeapp.RatingActivity;
import com.irmwrs.recipeapp.adapters.RecipeStepsAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecipeStepsFragment extends Fragment {

    private RecyclerView recipeStepsList;
    private Button btnCompleteRecipe;
    List<Step> steps;
    boolean showRatingPage;
    int userId; // todo get user id
    int recipeId;

    public RecipeStepsFragment(List<Step> steps, boolean showRatingPage, int userId, int recipeId) {
        // Required empty public constructor
        this.steps = steps;
        this.showRatingPage = showRatingPage;
        this.userId = userId;
        this.recipeId = recipeId;
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
                if(showRatingPage){
                    Intent intent = new Intent(getContext(), RatingActivity.class);
                    intent.putExtra("userId", userId);
                    intent.putExtra("recipeId", recipeId);
                    startActivity(intent);
                }
                else {
                    Intent intent1 = new Intent(getContext(), MainActivity.class);
                    intent1.putExtra("selectedTab", 3);
                    startActivity(intent1);
                }
            }
        });
    }
}