package com.irmwrs.recipeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;

public class OrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

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
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String date = day + "/" + getMonth(month+1) + "/" + year;
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

        calendar.add(Calendar.DAY_OF_MONTH, 10);
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
                // send data to server
                // navigate to cart
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