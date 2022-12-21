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

import com.irmwrs.recipeapp.Class.CartItem;
import com.irmwrs.recipeapp.Class.Ingredient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IngredientCartActivity extends AppCompatActivity implements IngredientCartAdapter.ViewHolder.OnCheckListener {

    List<CartItem> selected = new ArrayList<>();
    TextView tvTotalPrice;
    TextView tvSelected;
    List<Ingredient> ingredients;
    List<CartItem> cartItemList = new ArrayList<>();
    ArrayList<Integer> ids;
    ArrayList<Integer> qtys;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_cart);

        Intent intent = getIntent();
        ids = intent.getIntegerArrayListExtra("ids");
        qtys = intent.getIntegerArrayListExtra("qtys");
        Server server = new Server();

        server.getAllIngredient().enqueue(new Callback<List<Ingredient>>() {
            @Override
            public void onResponse(Call<List<Ingredient>> call, Response<List<Ingredient>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                    return;
                }
                ingredients = response.body();
                // inflate cartItems
                for (int i = 0; i < ids.size(); i++){
                    CartItem cartItem = new CartItem();
                    for (int j = 0; j < ingredients.size(); j++){
                        if(ingredients.get(j).id == ids.get(i)){
                            cartItem.name = ingredients.get(j).ingredientName;
                            cartItem.image = ingredients.get(j).ingredientImage;
                            cartItem.price = ingredients.get(j).ingredientPrice;
                        }
                    }
                    cartItem.ingredientId = ids.get(i);
                    cartItem.qty = qtys.get(i);
                    cartItemList.add(cartItem);
                    selected.add(cartItem);
                }

                IngredientCartAdapter adapter = new IngredientCartAdapter(IngredientCartActivity.this, cartItemList, IngredientCartActivity.this);

                tvSelected = findViewById(R.id.tvSelected);
                RecyclerView rvCart = findViewById(R.id.rvCart);
                tvTotalPrice = findViewById(R.id.tvTotalPrice);
                Button btnOrder = findViewById(R.id.btnOrder);

                String selectedItems = selected.size() + "/" + cartItemList.size() + " selected items";
                tvSelected.setText(selectedItems);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(IngredientCartActivity.this);
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
                            Intent intent = new Intent(IngredientCartActivity.this, AddToCartActivity.class);
                            setSummaryIntent(intent);
                            startActivity(intent);
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Ingredient>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
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
        ids = new ArrayList<>();
        qtys = new ArrayList<>();
        double delivery_cost = 10000; // default value
        for (int i = 0; i < selected.size(); i++){
            qty_name += selected.get(i).qty + "x " + selected.get(i).name + "\n";
            price += "Rp" + selected.get(i).price + "\n";
            ids.add((int) selected.get(i).ingredientId);
            qtys.add(selected.get(i).qty);
        }
        qty_name += "Biaya Kirim";
        price += "Rp" + delivery_cost;
        intent.putExtra("qty_name", qty_name);
        intent.putExtra("price", price);
        intent.putExtra("total", getTotalPrice() + delivery_cost);
        intent.putIntegerArrayListExtra("ids", ids);
        intent.putIntegerArrayListExtra("qtys", qtys);
    }

    @Override
    public void onCheckClick(int position, boolean isChecked, int qty) {
        if (qty == 0){
            if(isChecked){
                selected.add(cartItemList.get(position));
            }
            else {
                selected.remove(cartItemList.get(position));

            }
            String selectedItems = selected.size() + "/" + cartItemList.size() + " selected items";
            tvSelected.setText(selectedItems);
        }
        else {
            cartItemList.get(position).qty = qty;
        }
        String totalPrice = "Total price\nRp. " + getTotalPrice();
        tvTotalPrice.setText(totalPrice);
    }
}