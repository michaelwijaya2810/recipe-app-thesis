package com.irmwrs.recipeapp.cart;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.irmwrs.recipeapp.R;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    TextView tvSelected;
    RecyclerView rvCart;
    TextView tvTotalPrice;
    Button btnCheckout;

    CartAdapter adapter;
    LinearLayoutManager linearLayoutManager;

    List<CartOrderResponse> cartItems;
    List<CartOrderResponse> selected;
    int deliveryFee = 10000;

    public CartFragment(List<CartOrderResponse> cartItems) {
        this.cartItems = cartItems;
        this.selected = cartItems;
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

        // texts init
        String selectedItems = selected.size() + "/" + cartItems.size() + " selected items";
        tvSelected.setText(selectedItems);
        String totalPrice = "Total price + Delivery Fee\nRp. " + getTotalPrice();
        tvTotalPrice.setText(totalPrice);

        // buttons init
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        // recyclerview init
        linearLayoutManager = new LinearLayoutManager(getContext());
        adapter = new CartAdapter(getContext(), cartItems);
        rvCart.setLayoutManager(linearLayoutManager);
        rvCart.setAdapter(adapter);
    }

    String getTotalPrice(){
        int totalPrice = 0;
        for (int i = 0; i < selected.size(); i++){
            totalPrice = totalPrice + selected.get(i).totalPrice;
        }
        return totalPrice + " + " + deliveryFee;
    }
}