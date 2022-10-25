package com.irmwrs.recipeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class OrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        TextView tvQty = findViewById(R.id.tvQty);
        TextView tvName = findViewById(R.id.tvName);
        TextView tvPrice = findViewById(R.id.tvPrice);
        TextView tvTotalPrice2 = findViewById(R.id.tvTotalPrice2);

        Intent intent = getIntent();

        tvQty.setText(intent.getStringExtra("qty"));
        tvName.setText(intent.getStringExtra("name"));
        tvPrice.setText(intent.getStringExtra("price"));
        String totalPrice = "Total price: Rp. " + intent.getDoubleExtra("total", 0);
        tvTotalPrice2.setText(totalPrice);
    }
}