package com.irmwrs.recipeapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.irmwrs.recipeapp.R;
import com.irmwrs.recipeapp.adapters.RecipeListAdapter;

public class RecipeListFragment extends Fragment {

    private RecyclerView rvRecipeList;

    public RecipeListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvRecipeList = view.findViewById(R.id.rv_recipe_list);
        rvRecipeList.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        RecipeListAdapter adapter = new RecipeListAdapter();
        rvRecipeList.setAdapter(adapter);
    }
}