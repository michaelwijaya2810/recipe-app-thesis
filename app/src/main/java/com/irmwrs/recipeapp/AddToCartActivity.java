package com.irmwrs.recipeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.irmwrs.recipeapp.adapters.SummaryListAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddToCartActivity extends AppCompatActivity {

    String date;
    Functions functions;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);

        functions = new Functions(AddToCartActivity.this);
        sharedPreferences = getApplicationContext().getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        String address = sharedPreferences.getString("Address", "");

        TextView tvAddress = findViewById(R.id.tvAddress);
        tvAddress.setPaintFlags(tvAddress.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        //sample data
        tvAddress.setText(address);

        Button btnDatePicker = findViewById(R.id.btnDatePicker);
        TextView tvTotalPrice2 = findViewById(R.id.tvTotalPrice2);
        Button btnToPayment = findViewById(R.id.btnToPayment);

        Intent intent = getIntent();
        Context context = getApplicationContext();
        SharedPreferences sharepref = context.getSharedPreferences("userinfo",Context.MODE_PRIVATE);

        int userId;
        userId = sharepref.getInt("Userid",0);
        long recipeId = intent.getLongExtra("recipeId", 0);
        ArrayList<Integer> ids = intent.getIntegerArrayListExtra("ids");
        ArrayList<Integer> qtys = intent.getIntegerArrayListExtra("qtys");
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                date = year + "-" + (month+1) + "-" + day;
                btnDatePicker.setText(date);
            }
        };





        if(userId == 0)
        {
            Toast.makeText(this, "Invalid Login Session", Toast.LENGTH_SHORT).show();
            intent = new Intent(this, Login.class);
            startActivity(intent);
        }


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

        String[] qtyName = intent.getStringExtra("qty_name").split("\n");
        ArrayList<String> qtyNameList = new ArrayList<>(Arrays.asList(qtyName));
        String[] prices = intent.getStringExtra("price").split("\n");
        ArrayList<String> priceList = new ArrayList<>(Arrays.asList(prices));

        RecyclerView rvSummaryList = findViewById(R.id.summary_list);
        SummaryListAdapter summaryListAdapter = new SummaryListAdapter(qtyNameList, priceList);
        rvSummaryList.setAdapter(summaryListAdapter);
        rvSummaryList.setLayoutManager(new LinearLayoutManager(this));

        String price = intent.getStringExtra("total");
        tvTotalPrice2.setText(price);

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
                            functions.showToast(String.valueOf(response.code()));
                            return;
                        }
                        String authKey = response.body().value;
                        server.postOrder(userId, authKey, order).enqueue(new Callback<com.irmwrs.recipeapp.Class.ResponseClass.Response>() {
                            @Override
                            public void onResponse(Call<com.irmwrs.recipeapp.Class.ResponseClass.Response> call, Response<com.irmwrs.recipeapp.Class.ResponseClass.Response> response) {
                                if (!response.isSuccessful()){
                                    functions.dismissLoading();
                                    functions.showToast(String.valueOf(response.code()));
                                    return;
                                }
                                if(!response.body().errorReason.equals("")){
                                    functions.dismissLoading();
                                    functions.showToast(response.body().errorReason);
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
                                functions.showToast(t.getMessage());
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<Key> call, Throwable t) {
                        functions.dismissLoading();
                        functions.showToast(t.getMessage());
                    }
                });
            }
        });
    }
}