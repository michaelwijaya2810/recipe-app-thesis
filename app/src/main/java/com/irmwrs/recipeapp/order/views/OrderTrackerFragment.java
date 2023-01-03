package com.irmwrs.recipeapp.order.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.irmwrs.recipeapp.MainActivity;
import com.irmwrs.recipeapp.R;
import com.irmwrs.recipeapp.cart.CartAdapter;
import com.irmwrs.recipeapp.order.adapters.OrderTrackerAdapter;
import com.irmwrs.recipeapp.order.models.OrderHistoryResponse;
import com.irmwrs.recipeapp.payment.PaymentActivity;
import com.irmwrs.recipeapp.waiting_for_payment.WaitingForPaymentActivity;

import java.util.List;

public class OrderTrackerFragment extends Fragment implements OrderTrackerAdapter.OnOrderTrackerListener {

    List<OrderHistoryResponse> orderTrackerList;
    int deliveryFee = 10000;

    OrderTrackerAdapter adapter;
    LinearLayoutManager linearLayoutManager;

    RecyclerView rvOrderTracker;

    public OrderTrackerFragment(List<OrderHistoryResponse> orderTrackerList) {
        // Required empty public constructor
        this.orderTrackerList = orderTrackerList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_tracker, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view){
        // widget init
        rvOrderTracker = view.findViewById(R.id.rvOrderTracker);

        // recycler view init
        linearLayoutManager = new LinearLayoutManager(getContext());
        adapter = new OrderTrackerAdapter(getContext(), orderTrackerList, this, getActivity());
        rvOrderTracker.setLayoutManager(linearLayoutManager);
        rvOrderTracker.setAdapter(adapter);
    }

    @Override
    public void onOrderTrackerClick(int position, boolean isDelivered) {
        OrderHistoryResponse orderTracker = orderTrackerList.get(position);
        if(orderTracker.status.equals("Checkout") && !isDelivered){
            Intent intent = new Intent(getActivity(), WaitingForPaymentActivity.class);
            intent.putExtra("amount", orderTracker.order.totalPrice + deliveryFee);
            intent.putExtra("bankName", "BCA Virtual Account");
            intent.putExtra("accNumber", orderTracker.virtualNumber);
            startActivity(intent);
        }
        if(isDelivered){
            ((MainActivity)getActivity()).OrderFragment(); // refreshes history page
        }
    }
}