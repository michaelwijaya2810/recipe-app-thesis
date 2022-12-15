package com.irmwrs.recipeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Objects;

public class PaymentActivity extends AppCompatActivity {
    MaterialButtonToggleGroup tbPaymentOption;
    TextView tvTotalPrice3;
    Button btnPay;

    String paymentMethod = "";

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        init();
    }

    void init(){
        intent = getIntent();

        // widget init
        tbPaymentOption = findViewById(R.id.tbPaymentOption);
        tvTotalPrice3 = findViewById(R.id.tvTotalPrice3);
        btnPay = findViewById(R.id.btnPay);

        // text init
        String totalPrice = "Rp" + intent.getDoubleExtra("total_price", 0);
        tvTotalPrice3.setText(totalPrice);

        // button init
        tbPaymentOption.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked){
                    if (checkedId == R.id.btnBcaVA){
                        paymentMethod = "BCA";
                    }
                }
                else {
                    paymentMethod = "";
                }
            }
        });
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(paymentMethod.equals("")){
                    Toast.makeText(getApplicationContext(), "Please select a payment method", Toast.LENGTH_SHORT).show();
                }
                else {
                    // todo navigate to next page base on payment method
                }
            }
        });
    }
}