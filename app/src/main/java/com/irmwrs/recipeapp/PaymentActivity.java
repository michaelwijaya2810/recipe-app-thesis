package com.irmwrs.recipeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;

public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        MaterialButtonToggleGroup tbPaymentOption = findViewById(R.id.tbPaymentOption);
        int buttonId = tbPaymentOption.getCheckedButtonId();
        MaterialButton button = tbPaymentOption.findViewById(buttonId);
        TextView tvPaymentOptionBalance = findViewById(R.id.tvPaymentOptionBalance);
        TextView tvBalance = findViewById(R.id.tvBalance);
        TextView tvTotalPrice3 = findViewById(R.id.tvTotalPrice3);

        Intent intent = getIntent();

        //sample data
        double gopaySaldo = 1000000;
        double ovoSaldo = 500000;

        tbPaymentOption.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked){
                    if (checkedId == R.id.btnGopay){
                        String balance = "Rp" + gopaySaldo;
                        tvPaymentOptionBalance.setText("Gopay Saldo");
                        tvBalance.setText(balance);
                    }
                    else if(checkedId == R.id.btnOvo){
                        String balance = "Rp" + ovoSaldo;
                        tvPaymentOptionBalance.setText("Ovo Saldo");
                        tvBalance.setText(balance);
                    }
                }
                else {
                    tvPaymentOptionBalance.setText("");
                    tvBalance.setText("");
                }
            }
        });

        String totalPrice = "Rp" + intent.getDoubleExtra("total_price", 0);
        tvTotalPrice3.setText(totalPrice);
    }
}