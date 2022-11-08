package com.irmwrs.recipeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class EndPaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_payment);

        TextView tvEndPaymentTitle = findViewById(R.id.tvEndPaymentTitle);
        ImageView ivEndPayment = findViewById(R.id.ivEndPayment);
        TextView tvEndPaymentBody = findViewById(R.id.tvEndPaymentBody);
        Button btnBackToMainMenu = findViewById(R.id.btnBackToMainMenu);

        // sample data
        boolean isPaid = false;

        if(isPaid){
            tvEndPaymentTitle.setText("Paid");
            tvEndPaymentBody.setText("Payment success");
        }
        else {
            tvEndPaymentTitle.setText("Waiting payment");
            tvEndPaymentBody.setText("Waiting for successful payment");
        }

    }
}