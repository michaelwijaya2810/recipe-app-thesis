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

import com.irmwrs.recipeapp.R;
import com.irmwrs.recipeapp.order.adapters.HistoryAdapter;
import com.irmwrs.recipeapp.order.adapters.OrderTrackerAdapter;
import com.irmwrs.recipeapp.order.models.OrderHistoryResponse;

import java.util.List;

public class HistoryFragment extends Fragment {

    List<OrderHistoryResponse> historyList;

    HistoryAdapter adapter;
    LinearLayoutManager linearLayoutManager;

    RecyclerView rvHistory;

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
        init(view);
    }

    private void init(View view){
        // widget init
        rvHistory = view.findViewById(R.id.rvHistory);

        // recycler view init
        linearLayoutManager = new LinearLayoutManager(getContext());
        adapter = new HistoryAdapter(getContext(), historyList);
        rvHistory.setLayoutManager(linearLayoutManager);
        rvHistory.setAdapter(adapter);
    }
}