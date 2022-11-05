package com.irmwrs.recipeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
        Button btnToPayment = findViewById(R.id.btnToPayment);


        Intent intent = getIntent();

        tvQtyAndName.setText(intent.getStringExtra("qty_name"));
        tvPrice.setText(intent.getStringExtra("price"));
        double price = intent.getDoubleExtra("total", 0);
        String totalPrice = "Total price\nRp. " + price;
        tvTotalPrice2.setText(totalPrice);

        btnToPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderActivity.this, PaymentActivity.class);
                intent.putExtra("total_price", price);
                startActivity(intent);
            }
        });
    }
}