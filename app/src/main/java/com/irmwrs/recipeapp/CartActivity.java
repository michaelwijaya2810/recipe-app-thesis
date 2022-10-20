package com.irmwrs.recipeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        List<Ingredient> ingredientList = new ArrayList<>();


        // sample data
        Ingredient ingredient = new Ingredient();
        ingredient.name = "Tomato";
        ingredient.price = 20.0;
        ingredient.image = R.drawable.tomato;

        Ingredient ingredient2 = new Ingredient();
        ingredient2.name = "Apple";
        ingredient2.price = 30.0;
        ingredient2.image = R.drawable.apple;

        ingredientList.add(ingredient);
        ingredientList.add(ingredient2);
        ingredientList.add(ingredient);
        ingredientList.add(ingredient2);
        ingredientList.add(ingredient);
        ingredientList.add(ingredient2);
        ingredientList.add(ingredient);
        ingredientList.add(ingredient2);
        ingredientList.add(ingredient);
        ingredientList.add(ingredient2);
        ingredientList.add(ingredient);
        ingredientList.add(ingredient2);
        ingredientList.add(ingredient);
        ingredientList.add(ingredient2);
        ingredientList.add(ingredient);
        ingredientList.add(ingredient2);
        ingredientList.add(ingredient);
        ingredientList.add(ingredient2);
        ingredientList.add(ingredient);
        ingredientList.add(ingredient2);

        CartAdapter adapter = new CartAdapter(this, ingredientList);

        RecyclerView rvCart = findViewById(R.id.rvCart);
        TextView tvTotalPrice = findViewById(R.id.tvTotalPrice);
        Button btnOrder = findViewById(R.id.btnOrder);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvCart.setLayoutManager(linearLayoutManager);
        rvCart.setAdapter(adapter);

        String totalPrice = "Total price: Rp. " + getTotalPrice(ingredientList);

        tvTotalPrice.setText(totalPrice);

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, OrderActivity.class);
                startActivity(intent);
            }
        });
    }

    double getTotalPrice(List<Ingredient> ingredientList){
        double total = 0;
        for (int i = 0; i < ingredientList.size(); i++){
            total = total + ingredientList.get(i).price;
        }
        return total;
    }
}