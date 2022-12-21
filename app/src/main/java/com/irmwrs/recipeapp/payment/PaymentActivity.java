package com.irmwrs.recipeapp.payment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.gson.Gson;
import com.irmwrs.recipeapp.Class.Key;
import com.irmwrs.recipeapp.Functions;
import com.irmwrs.recipeapp.R;
import com.irmwrs.recipeapp.Server;
import com.irmwrs.recipeapp.waiting_for_payment.WaitingForPaymentActivity;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {
    MaterialButtonToggleGroup tbPaymentOption;
    TextView tvTotalPrice3;
    Button btnPay;

    String paymentMethod = "";
    int totalPrice;
    long userId = 8; // todo get userId from local
    ArrayList<Integer> orderList  = new ArrayList<>();

    Intent intent;
    Functions functions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        init();
    }

    void init(){
        functions = new Functions();

        // variable init
        intent = getIntent();
        totalPrice = (int) intent.getDoubleExtra("total_price", 0);
        orderList = intent.getIntegerArrayListExtra("order_list");

        // widget init
        tbPaymentOption = findViewById(R.id.tbPaymentOption);
        tvTotalPrice3 = findViewById(R.id.tvTotalPrice3);
        btnPay = findViewById(R.id.btnPay);

        // text init
        tvTotalPrice3.setText(functions.toRupiah((double) totalPrice));

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
                    functions.showToast(getApplicationContext(), "Please select a payment method");
                }
                else {
                    Server server = new Server();
                    functions.showLoading(PaymentActivity.this);
                    server.getAuthToken((int) userId, orderList).enqueue(new Callback<Key>() {
                        @Override
                        public void onResponse(Call<Key> call, Response<Key> response) {
                            if (!response.isSuccessful()){
                                functions.showToast(getApplicationContext(), String.valueOf(response.code()));
                                return;
                            }
                            String auth = response.body().value;
                            server.postCheckout(userId, auth, totalPrice, orderList).enqueue(new Callback<PaymentResponse>() {
                                @Override
                                public void onResponse(Call<PaymentResponse> call, Response<PaymentResponse> response) {
                                    if (!response.isSuccessful()){
                                        functions.showToast(getApplicationContext(), String.valueOf(response.code()));
                                        return;
                                    }
                                    Intent intent = new Intent(PaymentActivity.this, WaitingForPaymentActivity.class);
                                    intent.putExtra("amount", totalPrice);
                                    intent.putExtra("bankName", response.body().paymentInfo.bankName);
                                    intent.putExtra("accNumber", response.body().paymentInfo.virtualAccNumber);
                                    functions.dismissLoading();
                                    startActivity(intent);
                                }

                                @Override
                                public void onFailure(Call<PaymentResponse> call, Throwable t) {
                                    functions.showToast(getApplicationContext(), t.getMessage());
                                }
                            });
                        }

                        @Override
                        public void onFailure(Call<Key> call, Throwable t) {
                            functions.showToast(getApplicationContext(), t.getMessage());
                        }
                    });
                }
            }
        });
    }
}