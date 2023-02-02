package com.irmwrs.recipeapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.irmwrs.recipeapp.Class.Ingredient;
import com.irmwrs.recipeapp.Class.Recipe;
import com.irmwrs.recipeapp.Class.SingleRecipeIngredient;
import com.irmwrs.recipeapp.Class.UpdateRecipe;
import com.irmwrs.recipeapp.Class.UpdateRecipeIngredient;
import com.irmwrs.recipeapp.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AddOrEditRecipeIngredientsFragment extends Fragment {
    List<Ingredient> ingredients;
    Recipe recipe;
    List<SingleRecipeIngredient> recipeIngredients;
    Button btnSave;
    getIngredientForm getData;



    public AddOrEditRecipeIngredientsFragment(List<Ingredient> ingredients, Recipe recipe, List<SingleRecipeIngredient> recipeIngredients, Button btnSave, getIngredientForm getData) {
        // Required empty public constructor
        this.ingredients = ingredients;
        this.recipe = recipe;
        this.recipeIngredients = recipeIngredients;
        this.btnSave = btnSave;
        this.getData = getData;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_or_edit_recipe_ingredients, container, false);
    }

    ImageView ivRecipeImage;
    EditText etRecipeName;
    MaterialButtonToggleGroup tgDifficulty;
    EditText etRecipeDescription;
    ChipGroup cgIngredients;
    Spinner spinnerIngredient;
    EditText etQty;
    Button btnAddIngredient;
    Bitmap recipeImageBitmap;
    int difficulty;
    List<String> recipeIngredientName = new ArrayList<>();
    List<Integer> recipeIngredientQty = new ArrayList<>();
    long recipesId = 0;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivRecipeImage = view.findViewById(R.id.ivRecipeImage);
        etRecipeName = view.findViewById(R.id.etRecipeName);
        tgDifficulty = view.findViewById(R.id.tgDifficulty);
        etRecipeDescription = view.findViewById(R.id.etRecipeDescription);
        cgIngredients = view.findViewById(R.id.cgIngredients);
        spinnerIngredient = view.findViewById(R.id.spinnerIngredient);
        etQty = view.findViewById(R.id.etQty);
        btnAddIngredient = view.findViewById(R.id.btnAddIngredient);

        //Initialize Image
        if(recipe != null){// Edit page
            recipesId = recipe.id;
            if(recipe.recipeImage.equals("")){
                ivRecipeImage.setImageResource(R.drawable.no_image_placeholder);
                recipeImageBitmap = null;
            }
            else {
                //get image from URL and set it to Bitmap
                Picasso.get().load(recipe.recipeImage).into(target);
            }



            etRecipeName.setText(recipe.recipeName);
            if (recipe.recipeDifficulty == 1){
                difficulty = 1;
                tgDifficulty.check(R.id.tbEasy);
            }
            else if(recipe.recipeDifficulty == 2){
                difficulty = 2;
                tgDifficulty.check(R.id.tbMedium);
            }
            else{
                difficulty = 3;
                tgDifficulty.check(R.id.tbHard);
            }
            etRecipeDescription.setText(recipe.recipeDescription);
            for(int i = 0; i < recipeIngredients.size(); i++){
                createChip(recipeIngredients.get(i).ingredientName, recipeIngredients.get(i).qty, view);
            }
        }
        ivRecipeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nGetContent.launch("image/*");
            }
        });
        tgDifficulty.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if(isChecked){
                    if (checkedId == R.id.tbEasy){
                        difficulty = 1;
                    }
                    else if(checkedId == R.id.tbMedium){
                        difficulty = 2;
                    }
                    else if(checkedId == R.id.tbHard){
                        difficulty = 3;
                    }
                }
            }
        });
        List<String> ingredientsName = new ArrayList<>();
        for (int i = 0; i < ingredients.size(); i++){
            ingredientsName.add(ingredients.get(i).ingredientName);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item, ingredientsName);
        adapter.sort(new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {
                return s.compareTo(t1);
            }
        });
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerIngredient.setAdapter(adapter);
        btnAddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etQty.getText().toString().equals("")){
                    Toast.makeText(getContext(), "Qty can't be empty!", Toast.LENGTH_SHORT).show();
                } else if (spinnerIngredient.getSelectedItem().toString().isEmpty()){
                    Toast.makeText(getContext(), "Ingredient name can't be empty!", Toast.LENGTH_SHORT).show();
                } else {
                    if(!chipExist(spinnerIngredient.getSelectedItem().toString(), view)){ // check if chip already exist
                        createChip(spinnerIngredient.getSelectedItem().toString(), Integer.parseInt(etQty.getText().toString()), view);
                        etQty.getText().clear();
                    }
                }
            }
        });
    }
    ActivityResultLauncher<String> nGetContent = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if (result!= null){
                        try {
                            recipeImageBitmap = uriToBitmap(result);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ivRecipeImage.setImageBitmap(recipeImageBitmap);
                    }
                }
            }
    );

    public interface getIngredientForm{
        void getIngredientData(UpdateRecipe updateRecipe, boolean saveIsPressed);
    }

    Bitmap uriToBitmap(Uri imageUri) throws IOException {

        return MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
    }

    public UpdateRecipe getUpdatedIngredientData(){
        UpdateRecipe updateRecipe = new UpdateRecipe();
        updateRecipe.setRecipeImage(recipeImageBitmap);
        updateRecipe.recipeName = etRecipeName.getText().toString();
        updateRecipe.recipeDifficulty = difficulty;
        updateRecipe.recipeDescription = etRecipeDescription.getText().toString();
        List<UpdateRecipeIngredient> ingredientList = new ArrayList<>();
        for (int i = 0; i < recipeIngredientName.size(); i++){
            for (int j = 0; j < ingredients.size(); j++){
                if(ingredients.get(j).ingredientName.equals(recipeIngredientName.get(i))){
                    UpdateRecipeIngredient updateRecipeIngredient = new UpdateRecipeIngredient();
                    updateRecipeIngredient.recipesId = recipesId;
                    updateRecipeIngredient.ingredientId = ingredients.get(j).id;
                    updateRecipeIngredient.qty = recipeIngredientQty.get(i);
                    ingredientList.add(updateRecipeIngredient);
                }
            }
        }
        updateRecipe.ingredientList = ingredientList;
        return updateRecipe;
    }

    private Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            ivRecipeImage.setImageBitmap(bitmap);
            recipeImageBitmap = bitmap;
        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

    void createChip(String ingredientName, int ingredientQty, View view){
        Chip chip = new Chip(view.getContext());
        String chipText = ingredientName + " " + ingredientQty;
        chip.setText(chipText);
        chip.setCloseIconVisible(true);
        chip.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cgIngredients.removeView(chip);
            }
        });
        cgIngredients.addView(chip);
        recipeIngredientName.add(ingredientName);
        recipeIngredientQty.add(ingredientQty);
    }

    boolean chipExist(String ingredientName, View view){
        for(int i = 0; i < cgIngredients.getChildCount(); i++){
            Chip chip = (Chip) cgIngredients.getChildAt(i);
            if(chip.getText().toString().contains(ingredientName)){
                String[] split = chip.getText().toString().split(" ");
                int chipQty = Integer.parseInt(split[split.length-1]);
                int qty = Integer.parseInt(etQty.getText().toString()) + chipQty;
                String chipText = ingredientName + " " + qty;
                chip.setText(chipText);
                recipeIngredientQty.get(i).equals(qty);
                return true;
            }
        }
        return false;
    }

    @Override
    public void onStart() {
        super.onStart();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData.getIngredientData(getUpdatedIngredientData(), true);
            }
        });
        getData.getIngredientData(getUpdatedIngredientData(), false);
    }

    @Override
    public void onPause() {
        super.onPause();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData.getIngredientData(getUpdatedIngredientData(), true);
            }
        });
        getData.getIngredientData(getUpdatedIngredientData(), false);
    }

    @Override
    public void onResume() {
        super.onResume();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData.getIngredientData(getUpdatedIngredientData(), true);
            }
        });
        getData.getIngredientData(getUpdatedIngredientData(), false);
    }

}