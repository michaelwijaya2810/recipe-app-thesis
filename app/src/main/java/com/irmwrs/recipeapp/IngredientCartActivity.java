package com.irmwrs.recipeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.irmwrs.recipeapp.Class.CartItem;
import com.irmwrs.recipeapp.Class.Ingredient;
import com.irmwrs.recipeapp.adapters.IngredientCartAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IngredientCartActivity extends AppCompatActivity implements OnCheckListener {

    List<CartItem> selected = new ArrayList<>();
    TextView tvTotalPriceTitle;
    TextView tvTotalPrice;
    TextView tvSelected;
    List<Ingredient> ingredients;
    List<CartItem> cartItemList = new ArrayList<>();
    ArrayList<Integer> ids;
    ArrayList<Integer> qtys;
    long recipeId;
    Functions functions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_cart);
        Context context = getApplicationContext();

        tvSelected = findViewById(R.id.tvSelected);
        RecyclerView rvCart = findViewById(R.id.rvCart);
        tvTotalPriceTitle = findViewById(R.id.tvTotalPriceTitle);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        Button btnOrder = findViewById(R.id.btnOrder);

        functions = new Functions(IngredientCartActivity.this);
        SharedPreferences sp = context.getSharedPreferences("userinfo",context.MODE_PRIVATE);
        int userId =  sp.getInt("Userid",0);
        if (userId==0){
            functions.showToast("Invalid Login Session");
            Intent intent = new Intent(IngredientCartActivity.this, Login.class);
            startActivity(intent);
        }

        Intent intent = getIntent();
        ids = intent.getIntegerArrayListExtra("ids");
        qtys = intent.getIntegerArrayListExtra("qtys");
        recipeId = intent.getLongExtra("recipeId", 0);
        Server server = new Server();
        Functions functions = new Functions(IngredientCartActivity.this);

        functions.showLoading();
        server.getAllIngredient().enqueue(new Callback<List<Ingredient>>() {
            @Override
            public void onResponse(Call<List<Ingredient>> call, Response<List<Ingredient>> response) {
                if (!response.isSuccessful()){
                    functions.dismissLoading();
                    functions.showToast(String.valueOf(response.code()));
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
//                            cartItem.uom = ingredients.get(j).uom;
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

                String totalPrice = functions.toRupiah(getTotalPrice());
                tvTotalPrice.setText(totalPrice);

                btnOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(selected.size() == 0){
                            Toast.makeText(getApplicationContext(), "Please select one or more ingredients", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Intent intent = new Intent(IngredientCartActivity.this, AddToCartActivity.class);
                            intent.putExtra("recipeId", recipeId);
                            Log.i("testRecipe", String.valueOf(recipeId));
                            setSummaryIntent(intent);
                            startActivity(intent);
                        }
                    }
                });
                functions.dismissLoading();
            }

            @Override
            public void onFailure(Call<List<Ingredient>> call, Throwable t) {
                functions.dismissLoading();
                functions.showToast(t.getMessage());
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
        StringBuilder qty_name = new StringBuilder();
        StringBuilder price = new StringBuilder();
        ids = new ArrayList<>();
        qtys = new ArrayList<>();
        double delivery_cost = 10000; // default value
        for (int i = 0; i < selected.size(); i++){
            qty_name.append(selected.get(i).qty).append("x ").append(selected.get(i).name).append("\n");
            price.append(selected.get(i).getStringPrice()).append("\n");
            ids.add((int) selected.get(i).ingredientId);
            qtys.add(selected.get(i).qty);
        }
        qty_name.append("Biaya Kirim");
        price.append(functions.toRupiah(delivery_cost));
        intent.putExtra("qty_name", qty_name.toString());
        intent.putExtra("price", price.toString());
        intent.putExtra("total", functions.toRupiah(getTotalPrice() + delivery_cost));
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

        if (selected.isEmpty()) {
            tvTotalPriceTitle.setVisibility(View.GONE);
            tvTotalPrice.setVisibility(View.GONE);
        } else {
            tvTotalPriceTitle.setVisibility(View.VISIBLE);
            tvTotalPrice.setVisibility(View.VISIBLE);
            String totalPrice = functions.toRupiah(getTotalPrice());
            tvTotalPrice.setText(totalPrice);
        }
    }
}