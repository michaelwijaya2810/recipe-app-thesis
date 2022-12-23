package com.irmwrs.recipeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.irmwrs.recipeapp.Class.Key;
import com.irmwrs.recipeapp.Class.Order;
import com.irmwrs.recipeapp.Class.OrderIngredient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddToCartActivity extends AppCompatActivity {

    String date;
    Functions functions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);

        functions = new Functions(AddToCartActivity.this);

        TextView tvAddress = findViewById(R.id.tvAddress);
        tvAddress.setPaintFlags(tvAddress.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        //sample data
        tvAddress.setText("Gulag street 123");

        Button btnDatePicker = findViewById(R.id.btnDatePicker);
        TextView tvQtyAndName = findViewById(R.id.tvQtyAndName);
        TextView tvPrice = findViewById(R.id.tvPrice);
        TextView tvTotalPrice2 = findViewById(R.id.tvTotalPrice2);
        Button btnToPayment = findViewById(R.id.btnToPayment);

        Intent intent = getIntent();
        int userId = intent.getIntExtra("userId", 8); // get user id
        int recipeId = intent.getIntExtra("recipeId", 1); // get recipe id
        ArrayList<Integer> ids = intent.getIntegerArrayListExtra("ids");
        ArrayList<Integer> qtys = intent.getIntegerArrayListExtra("qtys");
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                date = year + "-" + (month+1) + "-" + day;
                btnDatePicker.setText(date);
            }
        };
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);

        calendar.add(Calendar.DAY_OF_MONTH, 2);
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

        calendar.add(Calendar.DAY_OF_MONTH, 8);
        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });
        tvQtyAndName.setText(intent.getStringExtra("qty_name"));
        tvPrice.setText(intent.getStringExtra("price"));
        double price = intent.getDoubleExtra("total", 0);
        String totalPrice = "Total price\nRp. " + price;
        tvTotalPrice2.setText(totalPrice);

        btnToPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(date == null){
                    functions.showToast("Please pick a delivery date");
                    return;
                }
                Order order = new Order();
                order.requestDeliveryDate = date;
                order.recipeId = recipeId;
                List<OrderIngredient> ingredient = new ArrayList<>();
                for (int i = 0; i < ids.size(); i++){
                    OrderIngredient orderIngredient = new OrderIngredient();
                    orderIngredient.ingredientid = ids.get(i);
                    orderIngredient.qty = qtys.get(i);
                    ingredient.add(orderIngredient);
                }
                order.ingredient = ingredient;

                Server server = new Server();
                functions.showLoading();
                server.getAuthToken(userId, order).enqueue(new Callback<Key>() {
                    @Override
                    public void onResponse(Call<Key> call, Response<Key> response) {
                        if (!response.isSuccessful()){
                            functions.dismissLoading();
                            Toast.makeText(getApplicationContext(), String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        String authKey = response.body().value;
                        server.postOrder(userId, authKey, order).enqueue(new Callback<com.irmwrs.recipeapp.Class.ResponseClass.Response>() {
                            @Override
                            public void onResponse(Call<com.irmwrs.recipeapp.Class.ResponseClass.Response> call, Response<com.irmwrs.recipeapp.Class.ResponseClass.Response> response) {
                                if (!response.isSuccessful()){
                                    functions.dismissLoading();
                                    Toast.makeText(getApplicationContext(), String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if(response.body().errorReason != null){
                                    functions.dismissLoading();
                                    Toast.makeText(getApplicationContext(), response.body().errorReason, Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                Intent intent = new Intent(AddToCartActivity.this, MainActivity.class);
                                intent.putExtra("pageNumber", 2);
                                functions.dismissLoading();
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(Call<com.irmwrs.recipeapp.Class.ResponseClass.Response> call, Throwable t) {
                                functions.dismissLoading();
                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<Key> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    String getMonth(int month){
        if(month == 1){
            return "January";
        }
        if(month == 2){
            return "February";
        }
        if(month == 3){
            return "March";
        }
        if(month == 4){
            return "April";
        }
        if(month == 5){
            return "May";
        }
        if(month == 6){
            return "June";
        }
        if(month == 7){
            return "July";
        }
        if(month == 8){
            return "August";
        }
        if(month == 9){
            return "September";
        }
        if(month == 10){
            return "October";
        }
        if(month == 11){
            return "November";
        }
        if(month == 12){
            return "December";
        }
        return "January";
    }
}