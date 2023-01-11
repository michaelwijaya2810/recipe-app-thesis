package com.irmwrs.recipeapp.home.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.irmwrs.recipeapp.AddOrEditRecipeActivity;
import com.irmwrs.recipeapp.Class.Recipe;
import com.irmwrs.recipeapp.MainActivity;
import com.irmwrs.recipeapp.R;
import com.irmwrs.recipeapp.RecipeDetailActivity;
import com.irmwrs.recipeapp.home.adapters.HomeAdapter;
import com.irmwrs.recipeapp.order.adapters.OrderTrackerAdapter;
import com.irmwrs.recipeapp.viewholders.RecipeViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class HomeFragment extends Fragment implements HomeAdapter.ViewHolder.OnRecipeListener {

    TextView tvWelcome;
    LinearLayout llSearchRecipe;
    LinearLayout llCreateRecipe;
    LinearLayout llSurpriseMe;
    TextView tvMoreHighlightedRecipe;
    RecyclerView rvHighlightedRecipe;
    TextView tvMoreTopRated;
    RecyclerView rvTopRated;
    TextView tvMoreRecentlyMade;
    RecyclerView rvRecentlyMade;

    List<Recipe> highlightedList = new ArrayList<>();
    List<Recipe> topRatedList = new ArrayList<>();
    List<Recipe> newestList;

    HomeAdapter adapterHighlightedRecipe;
    HomeAdapter adapterTopRated;
    HomeAdapter adapterRecentlyMade;
    LinearLayoutManager linearLayoutManager1;
    LinearLayoutManager linearLayoutManager2;
    LinearLayoutManager linearLayoutManager3;

    // todo get username
    String username = "Irfan";
    boolean isLogin = true;

    public HomeFragment(List<Recipe> recipes) {
        // Required empty public constructor
        newestList = recipes;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    void init(View view){
        // widget init
        tvWelcome = view.findViewById(R.id.tvWelcome);
        llSearchRecipe = view.findViewById(R.id.llSearchRecipe);
        llCreateRecipe = view.findViewById(R.id.llCreateRecipe);
        llSurpriseMe = view.findViewById(R.id.llSurpriseMe);
        tvMoreHighlightedRecipe = view.findViewById(R.id.tvMoreHighlightedRecipe);
        rvHighlightedRecipe = view.findViewById(R.id.rvHighlightedRecipe);
        tvMoreTopRated = view.findViewById(R.id.tvMoreTopRated);
        rvTopRated = view.findViewById(R.id.rvTopRated);
        tvMoreRecentlyMade = view.findViewById(R.id.tvMoreRecentlyMade);
        rvRecentlyMade = view.findViewById(R.id.rvRecentlyMade);

        // filtered list init
        setFilteredList();

        // texts init
        String welcomeMsg;
        if(isLogin){
            welcomeMsg = "Welcome chef " + username;
        }
        else {
            welcomeMsg = "Welcome chef";
        }
        tvWelcome.setText(welcomeMsg);

        // recyclerview init
            // Highlighted Recipes
        linearLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        adapterHighlightedRecipe = new HomeAdapter(getContext(), highlightedList, this);
        rvHighlightedRecipe.setLayoutManager(linearLayoutManager1);
        rvHighlightedRecipe.setAdapter(adapterHighlightedRecipe);

            // Top Rated
        linearLayoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        adapterTopRated = new HomeAdapter(getContext(), topRatedList, this);
        rvTopRated.setLayoutManager(linearLayoutManager2);
        rvTopRated.setAdapter(adapterTopRated);
        Log.i("testRecipe", String.valueOf(highlightedList.size()));

            // Recently Made
        linearLayoutManager3 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        adapterRecentlyMade = new HomeAdapter(getContext(), newestList, this);
        rvRecentlyMade.setLayoutManager(linearLayoutManager3);
        rvRecentlyMade.setAdapter(adapterRecentlyMade);

        // onClick init
        llSearchRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToRecipe(1);
            }
        });
        llCreateRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddOrEditRecipeActivity.class);
                startActivity(intent);

            }
        });
        llSurpriseMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RecipeDetailActivity.class);
                intent.putExtra("recipeId", getRandomId());
                startActivity(intent);
            }
        });
        tvMoreHighlightedRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToRecipe(2);
            }
        });
        tvMoreTopRated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToRecipe(3);
            }
        });
        tvMoreRecentlyMade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToRecipe(1);
            }
        });
    }

    void navigateToRecipe(int filter){
        ((MainActivity)getActivity()).bottomNav.setSelectedItemId(R.id.menu_1);
        ((MainActivity)getActivity()).RecipeFragment(true, filter);
    }

    void setFilteredList(){
        for (int i = 0; i < newestList.size(); i++){
            Recipe recipe = newestList.get(i);
            if(recipe.isHighlighted){
                highlightedList.add(recipe);
            }
            if(Integer.parseInt(recipe.recipeRating) >= 4){
                topRatedList.add(recipe);
            }
        }
    }

    int getRandomId(){
        int min = 0;
        int max = newestList.size() - 1;
        int random = new Random().nextInt((max - min) + 1) + min;
        return newestList.get(random).id;
    }

    @Override
    public void onRecipeClick(int id) {
        Intent intent = new Intent(getActivity(), RecipeDetailActivity.class);
        intent.putExtra("recipeId", id);
        startActivity(intent);
    }
}