package com.irmwrs.recipeapp.adapters;

import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.irmwrs.recipeapp.AddOrEditRecipeActivity;
import com.irmwrs.recipeapp.Class.Ingredient;
import com.irmwrs.recipeapp.Class.Recipe;
import com.irmwrs.recipeapp.Class.ResponseClass.SingleRecipeResponse;
import com.irmwrs.recipeapp.Class.SingleRecipeIngredient;
import com.irmwrs.recipeapp.Class.UpdateRecipe;
import com.irmwrs.recipeapp.fragments.AddOrEditRecipeIngredientsFragment;
import com.irmwrs.recipeapp.fragments.AddOrEditRecipeStepsFragment;

import java.util.List;

public class AddOrEditRecipeAdapter extends FragmentStateAdapter {
    List<Ingredient> ingredients;
    SingleRecipeResponse singleRecipeResponse;
    Button btnSave;
    AddOrEditRecipeIngredientsFragment.getIngredientForm getIngredient;
    AddOrEditRecipeStepsFragment.getStepForm getStep;
    public AddOrEditRecipeAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<Ingredient> ingredients, SingleRecipeResponse singleRecipeResponse, Button btnSave, AddOrEditRecipeIngredientsFragment.getIngredientForm getIngredient, AddOrEditRecipeStepsFragment.getStepForm getStep) {
        super(fragmentManager, lifecycle);
        this.ingredients = ingredients;
        this.singleRecipeResponse = singleRecipeResponse;
        this.btnSave = btnSave;
        this.getIngredient = getIngredient;
        this.getStep = getStep;
    }

    Fragment fragment;

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        fragment = new Fragment();
        switch (position){
            case 0:
                if(singleRecipeResponse == null){ // Add page
                    fragment = new AddOrEditRecipeIngredientsFragment(ingredients, null, null, btnSave, getIngredient);
                }
                else { // Edit page
                    fragment = new AddOrEditRecipeIngredientsFragment(ingredients, singleRecipeResponse.recipe, singleRecipeResponse.ingredients, btnSave, getIngredient);
                }
                fragment.getId();
                return fragment;
            case 1:
                if(singleRecipeResponse == null){ // Add page
                    fragment = new AddOrEditRecipeStepsFragment(null, getStep, btnSave);
                }
                else { // Edit page
                    fragment = new AddOrEditRecipeStepsFragment(singleRecipeResponse.steps, getStep, btnSave);
                }
                return fragment;
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }

}
