package com.irmwrs.recipeapp.cart;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.irmwrs.recipeapp.Functions;
import com.irmwrs.recipeapp.payment.PaymentActivity;
import com.irmwrs.recipeapp.R;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment implements CartAdapter.ViewHolder.OnCheckListener {

    TextView tvSelected;
    RecyclerView rvCart;
    TextView tvTotalPrice;
    Button btnCheckout;

    CartAdapter adapter;
    LinearLayoutManager linearLayoutManager;

    List<CartOrderResponse> cartItems;
    List<CartOrderResponse> selected = new ArrayList<>();
    int deliveryFee = 10000;
    double totalPrice;

    public CartFragment(List<CartOrderResponse> cartItems) {
        this.cartItems = cartItems;
        selected.addAll(cartItems);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    void init(View view){
        // widgets init
        tvSelected = view.findViewById(R.id.tvSelected);
        rvCart = view.findViewById(R.id.rvCart);
        tvTotalPrice = view.findViewById(R.id.tvTotalPrice);
        btnCheckout = view.findViewById(R.id.btnOrder);

        // buttons init
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selected.size() == 0){
                    Toast.makeText(getContext(), "Please select an item to checkout", Toast.LENGTH_SHORT).show();
                }
                else {
                    ArrayList<Integer> orderList = new ArrayList<>();
                    for (int i = 0; i < selected.size(); i++){
                        orderList.add((int) selected.get(i).orderId);
                    }
                    Intent intent = new Intent(getContext(), PaymentActivity.class);
                    intent.putExtra("total_price", totalPrice + deliveryFee);
                    intent.putIntegerArrayListExtra("order_list", orderList);
                    startActivity(intent);
                }
            }
        });

        // recyclerview init
        linearLayoutManager = new LinearLayoutManager(getContext());
        adapter = new CartAdapter(getContext(), cartItems, this);
        rvCart.setLayoutManager(linearLayoutManager);
        rvCart.setAdapter(adapter);

        // texts init
        updateText();
    }

    void updateText(){
        String selectedItems = selected.size() + "/" + cartItems.size() + " selected items";
        tvSelected.setText(selectedItems);
        String totalPrice = "Total price + Delivery Fee\n" + getTotalPrice();
        tvTotalPrice.setText(totalPrice);
    }

    String getTotalPrice(){
        totalPrice = 0;
        for (int i = 0; i < selected.size(); i++){
            totalPrice = totalPrice + selected.get(i).totalPrice;
        }
        Functions functions = new Functions();
        return functions.toRupiah(totalPrice) + " + " + functions.toRupiah((double) deliveryFee);
    }

    @Override
    public void onCheckClick(int position, boolean isChecked) {
        if(isChecked){
            selected.add(cartItems.get(position));
        }
        else {
            selected.remove(cartItems.get(position));
        }
        updateText();
    }
}