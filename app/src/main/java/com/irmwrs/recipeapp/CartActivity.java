package com.irmwrs.recipeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.irmwrs.recipeapp.Class.Recipe;
import com.irmwrs.recipeapp.Class.ResponseClass.RecipeListResponse;
import com.irmwrs.recipeapp.Class.ResponseClass.SingleRecipeResponse;
import com.irmwrs.recipeapp.Class.UpdateRecipe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity implements CartAdapter.ViewHolder.OnCheckListener {

    List<Ingredient> selected = new ArrayList<>();
    List<Ingredient> ingredientList = new ArrayList<>();
    TextView tvTotalPrice;
    TextView tvSelected;
    List<Recipe> recipes = new ArrayList<>();
    long id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Server server = new Server();
        server.getList("a").enqueue(new Callback<RecipeListResponse>() {
            @Override
            public void onResponse(Call<RecipeListResponse> call, Response<RecipeListResponse> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                    return;
                }
                recipes = response.body().data;
                id = recipes.get(0).id;
                Toast.makeText(getApplicationContext(), recipes.get(0).recipeName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<RecipeListResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
//        server.getSingleRecipe(1).enqueue(new Callback<SingleRecipeResponse>() {
//            @Override
//            public void onResponse(Call<SingleRecipeResponse> call, Response<SingleRecipeResponse> response) {
//                if (!response.isSuccessful()){
//                    Toast.makeText(getApplicationContext(), String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                Toast.makeText(getApplicationContext(), response.body().recipe.recipeName, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<SingleRecipeResponse> call, Throwable t) {
//
//            }
//        });

        // sample data
        Ingredient ingredient = new Ingredient();
        ingredient.name = "Tomato";
        ingredient.price = 20.0;
        ingredient.image = R.drawable.tomato;
        ingredient.qty = 1;

        Ingredient ingredient2 = new Ingredient();
        ingredient2.name = "Apple";
        ingredient2.price = 30.0;
        ingredient2.image = R.drawable.apple;
        ingredient2.qty = 2;

        ingredientList.add(ingredient);
        ingredientList.add(ingredient2);
        selected.add(ingredient);
        selected.add(ingredient2);

        CartAdapter adapter = new CartAdapter(this, ingredientList, this);

        tvSelected = findViewById(R.id.tvSelected);
        RecyclerView rvCart = findViewById(R.id.rvCart);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        Button btnOrder = findViewById(R.id.btnOrder);

        String selectedItems = selected.size() + "/" + ingredientList.size() + " selected items";
        tvSelected.setText(selectedItems);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvCart.setLayoutManager(linearLayoutManager);
        rvCart.setAdapter(adapter);

        String totalPrice = "Total price\nRp. " + getTotalPrice();
        tvTotalPrice.setText(totalPrice);

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selected.size() == 0){
                    Toast.makeText(getApplicationContext(), "Please select one or more ingredients", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(CartActivity.this, OrderActivity.class);
                    setSummaryIntent(intent);
                    startActivity(intent);
                }
            }
        });
    }

    double getTotalPrice(){
        double total = 0;
        for (int i = 0; i < selected.size(); i++){
            total = total + (selected.get(i).price * selected.get(i).qty);
        }
        return total;
    }

    void setSummaryIntent(Intent intent){
        String qty_name = "";
        String price = "";
        double delivery_cost = 11000;
        for (int i = 0; i < selected.size(); i++){
            qty_name += selected.get(i).qty + "x " + selected.get(i).name + "\n";
            price += "Rp" + selected.get(i).price + "\n";
        }
        qty_name += "Biaya Kirim";
        price += "Rp" + delivery_cost;
        intent.putExtra("qty_name", qty_name);
        intent.putExtra("price", price);
        intent.putExtra("total", getTotalPrice() + delivery_cost);
    }

    @Override
    public void onCheckClick(int position, boolean isChecked, int qty) {
        if (qty == 0){
            if(isChecked){
                selected.add(ingredientList.get(position));
            }
            else {
                selected.remove(ingredientList.get(position));
            }
            String selectedItems = selected.size() + "/" + ingredientList.size() + " selected items";
            tvSelected.setText(selectedItems);
        }
        else {
            ingredientList.get(position).qty = qty;
        }
        String totalPrice = "Total price\nRp. " + getTotalPrice();
        tvTotalPrice.setText(totalPrice);
    }
}