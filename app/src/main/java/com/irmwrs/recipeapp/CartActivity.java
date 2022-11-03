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

import com.irmwrs.recipeapp.Class.ChangePassword;
import com.irmwrs.recipeapp.Class.Recipe;
import com.irmwrs.recipeapp.Class.ResponseClass.LoginResponse;
import com.irmwrs.recipeapp.Class.ResponseClass.RecipeListResponse;
import com.irmwrs.recipeapp.Class.ResponseClass.SingleRecipeResponse;
import com.irmwrs.recipeapp.Class.ResponseClass.UserResponse;
import com.irmwrs.recipeapp.Class.Step;
import com.irmwrs.recipeapp.Class.UpdateRecipe;
import com.irmwrs.recipeapp.Class.UpdateRecipeIngredient;
import com.irmwrs.recipeapp.Class.UserRegister;
import com.irmwrs.recipeapp.Class.Validate;

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
        UserRegister userRegister = new UserRegister();
        userRegister.address = "Baker Street";
        userRegister.firstName = "Irfan";
        userRegister.lastName = "Suardhika";
        userRegister.password = "Password123";
        userRegister.username = "Ifn";
        userRegister.phoneNumber = "0811821289";
        userRegister.email = "irfan@irfan.com";

        String password = "Password1234";
        ChangePassword changePassword = new ChangePassword();
        changePassword.userid = "11";
        changePassword.oldPassword = password;
        changePassword.newPassword = "Password12345";
        password = changePassword.newPassword;

        server.postChangePassword(changePassword).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

//        server.postLogin(userRegister.username, password).enqueue(new Callback<LoginResponse>() {
//            @Override
//            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
//                if (!response.isSuccessful()){
//                    Toast.makeText(getApplicationContext(), String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                server.postUserDetail(response.body().userid).enqueue(new Callback<UserResponse>() {
//                    @Override
//                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
//                        if (!response.isSuccessful()){
//                            Toast.makeText(getApplicationContext(), String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                        Toast.makeText(getApplicationContext(), response.body().user.firstName, Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onFailure(Call<UserResponse> call, Throwable t) {
//                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(Call<LoginResponse> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });

        server.getRecipeListFromKeyword("a").enqueue(new Callback<RecipeListResponse>() {
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