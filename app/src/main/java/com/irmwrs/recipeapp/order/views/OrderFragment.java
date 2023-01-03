package com.irmwrs.recipeapp.order.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.irmwrs.recipeapp.R;
import com.irmwrs.recipeapp.adapters.RecipeDetailAdapter;
import com.irmwrs.recipeapp.order.adapters.OrderAdapter;
import com.irmwrs.recipeapp.order.models.OrderHistoryResponse;

import java.util.List;

public class OrderFragment extends Fragment {

    TabLayout tabLayout;
    TabLayoutMediator mediator;
    ViewPager2 viewPager2;
    OrderAdapter orderAdapter;

    List<OrderHistoryResponse> orders;

    public OrderFragment(List<OrderHistoryResponse> orders) {
        // Required empty public constructor
        this.orders = orders;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    void init(View view){
        // widget init
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager2 = view.findViewById(R.id.recipe_detail_pager);

        // tabs init
        orderAdapter = new OrderAdapter(getActivity().getSupportFragmentManager(), getLifecycle(), orders);
        viewPager2.setAdapter(orderAdapter);
        mediator = new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> {
                    if (position == 0) {
                        tab.setText("Order Tracker");
                    } else if (position == 1) {
                        tab.setText("History");
                    }
                });
        mediator.attach();


    }
}