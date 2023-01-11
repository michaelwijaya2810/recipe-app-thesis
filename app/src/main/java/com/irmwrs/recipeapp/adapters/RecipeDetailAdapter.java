package com.irmwrs.recipeapp.adapters;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.irmwrs.recipeapp.Class.ResponseClass.SingleRecipeResponse;
import com.irmwrs.recipeapp.fragments.RecipeIngredientsFragment;
import com.irmwrs.recipeapp.fragments.RecipeStepsFragment;

public class RecipeDetailAdapter extends FragmentStateAdapter {
    SingleRecipeResponse singleRecipeResponse;
    String author;
    boolean isLogin;
    int userId; // todo get user id
    public RecipeDetailAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, SingleRecipeResponse singleRecipeResponse, String author, boolean isLogin, int userId) {
        super(fragmentManager, lifecycle);
        this.singleRecipeResponse = singleRecipeResponse;
        this.author = author;
        this.isLogin = isLogin;
        this.userId = userId;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = new Fragment();
        switch (position){
            case 0:
                fragment = new RecipeIngredientsFragment(singleRecipeResponse.recipe, singleRecipeResponse.ingredients, author);
                return fragment;
            case 1:
                fragment = new RecipeStepsFragment(singleRecipeResponse.steps, showRatingPage(), userId, singleRecipeResponse.recipe.id);
                return fragment;
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    boolean showRatingPage(){
        if (isLogin && !singleRecipeResponse.isReviewed){
            return true;
        }
        return false;
    }
}
