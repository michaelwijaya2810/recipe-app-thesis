package com.irmwrs.recipeapp.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.irmwrs.recipeapp.IngredientCartActivity;
import com.irmwrs.recipeapp.Class.Recipe;
import com.irmwrs.recipeapp.Class.SingleRecipeIngredient;
import com.irmwrs.recipeapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecipeIngredientsFragment extends Fragment {

    Recipe recipe;
    List<SingleRecipeIngredient> ingredients;
    String author;
    List<SingleRecipeIngredient> highlightedIngredients;
    int i;
    ChipGroup recipe_ingredients_group;

    public RecipeIngredientsFragment(Recipe recipe, List<SingleRecipeIngredient> ingredients, String author) {
        // Required empty public constructor
        this.recipe = recipe;
        this.ingredients = ingredients;
        this.author = author;
        highlightedIngredients = new ArrayList<>(ingredients);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_ingredients, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView recipe_image = view.findViewById(R.id.recipe_image);
        TextView recipe_title_text = view.findViewById(R.id.recipe_title_text);
        TextView recipe_subtitle_text = view.findViewById(R.id.recipe_subtitle_text);
        TextView recipe_author_text = view.findViewById(R.id.recipe_author_text);
        TextView recipe_rating_value = view.findViewById(R.id.recipe_rating_value);
        TextView recipe_desc = view.findViewById(R.id.recipe_desc);
        recipe_ingredients_group = view.findViewById(R.id.recipe_ingredients_group);
        TextView order_ingredients_title = view.findViewById(R.id.order_ingredients_title);
        Button btn_order_ingredients = view.findViewById(R.id.btn_order_ingredients);

        if(recipe.recipeImage == ""){
            recipe_image.setImageResource(R.drawable.no_image_placeholder);
        }
        else {
            Picasso.get().load(recipe.recipeImage).into(recipe_image);

//            byte[] decodedBytes = Base64.decode(recipe.recipeImage, Base64.DEFAULT);
//            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
//            recipe_image.setImageBitmap(bitmap);
        }
        recipe_title_text.setText(recipe.recipeName);
        if (recipe.recipeDifficulty == 1){
            recipe_subtitle_text.setText("Difficulty: Easy");
        }
        else if(recipe.recipeDifficulty == 2){
            recipe_subtitle_text.setText("Difficulty: Medium");
        }
        else if(recipe.recipeDifficulty == 3){
            recipe_subtitle_text.setText("Difficulty: Hard");
        }
        recipe_author_text.setText("By " + author);
        recipe_rating_value.setText(recipe.recipeRating);
        recipe_desc.setText(recipe.recipeDescription);

        String ingredientText;
        for(i = 0; i < ingredients.size(); i++){
            Chip chip = new Chip(view.getContext());
            ingredientText = ingredients.get(i).ingredientName + " " + ingredients.get(i).qty;
            chip.setText(ingredientText);
            chip.setCheckable(true);
            chip.setClickable(true);
            chip.setChecked(true);
            chip.setId(i);
            chip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(chip.isChecked()){
                        highlightedIngredients.add(ingredients.get(chip.getId()));
                        if (highlightedIngredients.size() > 0) {
                            order_ingredients_title.setVisibility(View.VISIBLE);
                            btn_order_ingredients.setVisibility(View.VISIBLE);
                        }
                    }
                    else {
                        highlightedIngredients.remove(ingredients.get(chip.getId()));
                        if (highlightedIngredients.isEmpty()) {
                            order_ingredients_title.setVisibility(View.GONE);
                            btn_order_ingredients.setVisibility(View.GONE);
                        }
                    }
                }
            });
            recipe_ingredients_group.addView(chip);
        }

        btn_order_ingredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), IngredientCartActivity.class);
                ArrayList<Integer> ids = new ArrayList<>();
                ArrayList<Integer> qtys = new ArrayList<>();
                for (int i = 0; i < highlightedIngredients.size(); i++){
                    ids.add((int) highlightedIngredients.get(i).ingredientId);
                    qtys.add(highlightedIngredients.get(i).qty);
                }
                intent.putIntegerArrayListExtra("ids", ids);
                intent.putIntegerArrayListExtra("qtys", qtys);
                intent.putExtra("recipeId", Long.valueOf(recipe.id));
                startActivity(intent);
            }
        });
    }
}