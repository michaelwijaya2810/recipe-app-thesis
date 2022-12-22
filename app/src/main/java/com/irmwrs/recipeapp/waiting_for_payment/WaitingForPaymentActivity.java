package com.irmwrs.recipeapp.waiting_for_payment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.irmwrs.recipeapp.Functions;
import com.irmwrs.recipeapp.MainActivity;
import com.irmwrs.recipeapp.R;

public class WaitingForPaymentActivity extends AppCompatActivity {
    TextView tvAmount;
    TextView tvBankName;
    TextView tvAccNumber;
    Button btnBackToHome;

    String amount;
    String bankName;
    String accNumber;

    Intent intent;
    Functions functions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_for_payment);
        init();
    }

    void init(){
        // variable init
        intent = getIntent();
        functions = new Functions(WaitingForPaymentActivity.this);
        amount = "Please pay " + functions.toRupiah((double) intent.getIntExtra("amount", 0)) + " to:";
        bankName = intent.getStringExtra("bankName");
        accNumber = intent.getStringExtra("accNumber");

        // widget init
        tvAmount = findViewById(R.id.tvAmount);
        tvBankName = findViewById(R.id.tvBankName);
        tvAccNumber = findViewById(R.id.tvAccNumber);
        btnBackToHome = findViewById(R.id.btnBackToHome);

        // texts init
        tvAmount.setText(amount);
        tvBankName.setText(bankName);
        tvAccNumber.setText(accNumber);

        // button init
        btnBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WaitingForPaymentActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}