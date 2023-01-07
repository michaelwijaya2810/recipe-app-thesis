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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
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
    private Chip chipNewToOld;
    private Chip chipHighlighted;
    private Chip chipTopRated;
    List<Recipe> recipes;
    List<Recipe> currList;
    List<Recipe> highlightedList = new ArrayList<>();
    List<Recipe> topRatedList = new ArrayList<>();
    List<Recipe> searches = new ArrayList<>();
    RecipeListAdapter adapter;
    Context ctx;
    boolean isSearch;
    int filter;

    public RecipeListFragment(List<Recipe> recipes, Context ctx, boolean isSearch, int filter) {
        this.recipes = recipes;
        this.ctx = ctx;
        this.isSearch = isSearch;
        this.filter = filter;
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
        chipNewToOld = view.findViewById(R.id.chipNewToOld);
        chipHighlighted = view.findViewById(R.id.chipHighlighted);
        chipTopRated = view.findViewById(R.id.chipTopRated);
        rvRecipeList = view.findViewById(R.id.rv_recipe_list);
        tvNoMatches = view.findViewById(R.id.tvNoMatches);
        if(isSearch){ // if it comes from Home -> Search Recipe
            svRecipeList.requestFocus();
        }
        else{
            svRecipeList.clearFocus();
        }

        // filtered list init
        setFilteredList();

        // recyclerview init
        rvRecipeList.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        adapter = new RecipeListAdapter(recipes, this);
        rvRecipeList.setAdapter(adapter);

        // search init
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

        // filter init
        chipNewToOld.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked){
                    filterList(chipNewToOld.getText().toString());
                }
            }
        });
        chipHighlighted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked){
                    filterList(chipHighlighted.getText().toString());
                }
            }
        });
        chipTopRated.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked){
                    filterList(chipTopRated.getText().toString());
                }
            }
        });
        setCurrentFilter();
    }

    void searchList(String text){
        searches.clear();
        for(int i = 0; i < currList.size(); i++){
            Recipe recipe = currList.get(i);
            if(recipe.recipeName.toLowerCase().contains(text.toLowerCase())){
                searches.add(recipe);
            }
        }
        checkEmptyList(searches);
        adapter.updateList(searches);
    }

    void setCurrentFilter(){
        if(filter == 1){
            chipNewToOld.setChecked(true);
        }
        else if(filter == 2){
            chipHighlighted.setChecked(true);
        }
        else if(filter == 3){
            chipTopRated.setChecked(true);
        }
    }

    void setFilteredList(){
        for (int i = 0; i < recipes.size(); i++){
            Recipe recipe = recipes.get(i);
            if(recipe.isHighlighted){
                highlightedList.add(recipe);
            }
            if(Integer.parseInt(recipe.recipeRating) >= 4){
                topRatedList.add(recipe);
            }
        }
    }

    void filterList(String filter){
        switch (filter) {
            case "Newest To Oldest":
                currList = new ArrayList<>(recipes);
                checkEmptyList(currList);
                if (isSearchViewEmpty()) {
                    adapter.updateList(currList);

                } else {
                    searchList(svRecipeList.getQuery().toString());
                }
                break;
            case "Highlighted":
                currList = new ArrayList<>(highlightedList);
                checkEmptyList(currList);
                if (isSearchViewEmpty()) {
                    adapter.updateList(currList);

                } else {
                    searchList(svRecipeList.getQuery().toString());
                }
                break;
            case "Top Rated":
                currList = new ArrayList<>(topRatedList);
                checkEmptyList(currList);
                if (isSearchViewEmpty()) {
                    adapter.updateList(currList);

                } else {
                    searchList(svRecipeList.getQuery().toString());
                }
                break;
        }
    }

    void checkEmptyList(List<Recipe> list){
        if (list.size() == 0){
            tvNoMatches.setVisibility(View.VISIBLE);
        }
        else {
            tvNoMatches.setVisibility(View.GONE);
        }
    }

    boolean isSearchViewEmpty(){
        String search = svRecipeList.getQuery().toString();
        Log.i("testRecipe", search);
        return search.equals("");
    }

    @Override
    public void onRecipeClick(int position) {
        Intent intent = new Intent(getContext(), RecipeDetailActivity.class);
        startActivity(intent);
    }
}