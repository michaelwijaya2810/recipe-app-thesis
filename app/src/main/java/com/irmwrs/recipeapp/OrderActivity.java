package com.irmwrs.recipeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class OrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        TextView tvAddress = findViewById(R.id.tvAddress);
        tvAddress.setPaintFlags(tvAddress.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        //sample data
        tvAddress.setText("Gulag street 123");

        TextView tvQtyAndName = findViewById(R.id.tvQtyAndName);
        TextView tvPrice = findViewById(R.id.tvPrice);
        TextView tvTotalPrice2 = findViewById(R.id.tvTotalPrice2);

        Intent intent = getIntent();

        tvQtyAndName.setText(intent.getStringExtra("qty_name"));
        tvPrice.setText(intent.getStringExtra("price"));
        String totalPrice = "Total price\nRp. " + intent.getDoubleExtra("total", 0);
        tvTotalPrice2.setText(totalPrice);
    }
}