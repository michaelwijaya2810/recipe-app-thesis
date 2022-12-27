package com.irmwrs.recipeapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.irmwrs.recipeapp.Class.Recipe;
import com.irmwrs.recipeapp.MainActivity;
import com.irmwrs.recipeapp.R;
import com.irmwrs.recipeapp.RecipeDetailActivity;
import com.irmwrs.recipeapp.Server;
import com.irmwrs.recipeapp.adapters.RecipeListAdapter;
import com.irmwrs.recipeapp.viewholders.RecipeViewHolder;

import java.util.ArrayList;
import java.util.List;

public class RecipeListFragment extends Fragment implements RecipeViewHolder.OnRecipeListener {

    private RecyclerView rvRecipeList;
    private SearchView svRecipeList;
    private TextView tvNoMatches;
    List<Recipe> recipes = new ArrayList<>();
    List<Recipe> searches = new ArrayList<>();
    RecipeListAdapter adapter;
    Context ctx;

    public RecipeListFragment(List<Recipe> recipes, Context ctx) {
        this.recipes = recipes;
        this.ctx = ctx;
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
        init(view);
    }

    void init(View view){
        // widget init
        svRecipeList = view.findViewById(R.id.svRecipeList);
        rvRecipeList = view.findViewById(R.id.rv_recipe_list);
        tvNoMatches = view.findViewById(R.id.tvNoMatches);
        svRecipeList.clearFocus();

        // recyclerview init
        rvRecipeList.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        adapter = new RecipeListAdapter(recipes, this);
        rvRecipeList.setAdapter(adapter);

        // button init
        svRecipeList.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });
    }

    void searchList(String text){
        searches.clear();
        for(int i = 0; i < recipes.size(); i++){
            Recipe recipe = recipes.get(i);
            if(recipe.recipeName.toLowerCase().contains(text.toLowerCase())){
                searches.add(recipe);
            }
        }
        adapter.updateList(searches);
        if (searches.size() == 0){
            tvNoMatches.setVisibility(View.VISIBLE);
        }
        else {
            tvNoMatches.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRecipeClick(int position) {
        Intent intent = new Intent(getContext(), RecipeDetailActivity.class);
        intent.putExtra("recipeId", recipes.get(position).id);
        startActivity(intent);
    }
}