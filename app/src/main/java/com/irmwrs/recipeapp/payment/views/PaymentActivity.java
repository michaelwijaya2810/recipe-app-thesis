package com.irmwrs.recipeapp.payment.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.irmwrs.recipeapp.Class.Key;
import com.irmwrs.recipeapp.Functions;
import com.irmwrs.recipeapp.Login;
import com.irmwrs.recipeapp.R;
import com.irmwrs.recipeapp.Server;
import com.irmwrs.recipeapp.payment.models.PaymentResponse;
import com.irmwrs.recipeapp.waiting_for_payment.WaitingForPaymentActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {
    MaterialButtonToggleGroup tbPaymentOption;
    TextView tvTotalPrice3;
    Button btnPay;
    Button btnBcaVa;
    Button btnMandiriVa;

    int paymentMethod = 0;
    int totalPrice;

    long userId;
    Intent intent;
    Functions functions;
    ArrayList<Integer> orderList  = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_payment);
        Context context = PaymentActivity.this;
        SharedPreferences sharepref = context.getSharedPreferences("userinfo",Context.MODE_PRIVATE);

        userId = sharepref.getInt("Userid",0);

        if(userId == 0)
        {
            Toast.makeText(context, "Invalid Login Session", Toast.LENGTH_SHORT).show();

            Intent intentlogin = new Intent(context, Login.class);
            startActivity(intentlogin);
            finish();
        }
        init();
    }

    void init(){
        functions = new Functions(PaymentActivity.this);

        // variable init
        intent = getIntent();
        totalPrice = (int) intent.getDoubleExtra("total_price", 0);
        orderList = intent.getIntegerArrayListExtra("order_list");

        // widget init
        tbPaymentOption = findViewById(R.id.tbPaymentOption);
        tvTotalPrice3 = findViewById(R.id.tvTotalPrice3);
        btnPay = findViewById(R.id.btnPay);
        btnBcaVa = findViewById(R.id.btnBcaVA);
        btnMandiriVa = findViewById(R.id.btnMandiriVA);

        // text init
        tvTotalPrice3.setText(functions.toRupiah((double) totalPrice));

        // button init
        tbPaymentOption.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked){
                    if (checkedId == R.id.btnBcaVA){
                        paymentMethod = 1;
                        btnBcaVa.setBackgroundColor(getColor(R.color.link_blue));
                        btnMandiriVa.setBackgroundColor(getColor(R.color.yellow));
                    }
                    else if(checkedId == R.id.btnMandiriVA)
                    {
                        paymentMethod = 2;
                        btnBcaVa.setBackgroundColor(getColor(R.color.yellow));
                        btnMandiriVa.setBackgroundColor(getColor(R.color.link_blue));
                    }

                }
                else {
                    paymentMethod = 0;
                    btnBcaVa.setBackgroundColor(getColor(R.color.yellow));
                    btnMandiriVa.setBackgroundColor(getColor(R.color.yellow));
                }
            }
        });
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(paymentMethod == 0){
                    functions.showToast("Please select a payment method");
                }
                else {
                    Server server = new Server();
                    functions.showLoading();
                    server.getAuthToken((int) userId, orderList).enqueue(new Callback<Key>() {
                        @Override
                        public void onResponse(Call<Key> call, Response<Key> response) {
                            if (!response.isSuccessful()){
                                functions.dismissLoading();
                                functions.showToast(String.valueOf(response.code()));
                                return;
                            }
                            String auth = response.body().value;
                            server.postCheckout(userId, auth, totalPrice,paymentMethod, orderList).enqueue(new Callback<PaymentResponse>() {
                                @Override
                                public void onResponse(Call<PaymentResponse> call, Response<PaymentResponse> response) {
                                    if (!response.isSuccessful()){
                                        functions.dismissLoading();
                                        functions.showToast(String.valueOf(response.body().response.errorReason));
                                        return;
                                    }

                                    if(response.body().response != null){
                                        functions.dismissLoading();
                                        functions.showToast(response.body().response.errorReason);
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
            }
        });
    }
}