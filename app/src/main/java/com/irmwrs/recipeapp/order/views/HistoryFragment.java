package com.irmwrs.recipeapp.order.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.google.android.material.chip.Chip;
import com.irmwrs.recipeapp.R;
import com.irmwrs.recipeapp.order.adapters.HistoryAdapter;
import com.irmwrs.recipeapp.order.adapters.OrderTrackerAdapter;
import com.irmwrs.recipeapp.order.models.OrderHistoryResponse;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    List<OrderHistoryResponse> historyList;
    List<OrderHistoryResponse> paymentExpiredList = new ArrayList<>();
    List<OrderHistoryResponse> deliveredList = new ArrayList<>();

    HistoryAdapter adapter;
    LinearLayoutManager linearLayoutManager;

    RecyclerView rvHistory;
    Chip chipAll;
    Chip chipPaymentExpired;
    Chip chipDelivered;


    public HistoryFragment(List<OrderHistoryResponse> historyList) {
        // Required empty public constructor
        this.historyList = historyList;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setFilteredList();
        init(view);
    }

    private void init(View view){
        // widget init
        rvHistory = view.findViewById(R.id.rvHistory);
        chipAll = view.findViewById(R.id.chipAll);
        chipPaymentExpired = view.findViewById(R.id.chipPaymentExpired);
        chipDelivered = view.findViewById(R.id.chipDelivered);

        // recycler view init
        linearLayoutManager = new LinearLayoutManager(getContext());
        adapter = new HistoryAdapter(getContext(), historyList);
        rvHistory.setLayoutManager(linearLayoutManager);
        rvHistory.setAdapter(adapter);

        // chip init
        chipAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked){
                    filterList(chipAll.getText().toString());
                }
            }
        });
        chipPaymentExpired.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked){
                    filterList(chipPaymentExpired.getText().toString());
                }
            }
        });
        chipDelivered.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked){
                    filterList(chipDelivered.getText().toString());
                }
            }
        });
    }

    void setFilteredList(){
        for (int i = 0; i < historyList.size(); i++){
            OrderHistoryResponse history = historyList.get(i);
            if(history.status.equals("Payment Expired")){
                paymentExpiredList.add(history);
            }
            else if(history.status.equals("Delivered")){
                deliveredList.add(history);
            }

        }
    }

    void filterList(String filter){
        if(filter.equals("All")){
            adapter.updateList(historyList);
        }
        else if(filter.equals("Payment Expired")){
            adapter.updateList(paymentExpiredList);
        }
        else if(filter.equals("Delivered")){
            adapter.updateList(deliveredList);
        }

    }
}