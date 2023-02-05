package com.irmwrs.recipeapp.order.adapters;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.irmwrs.recipeapp.fragments.RecipeIngredientsFragment;
import com.irmwrs.recipeapp.fragments.RecipeStepsFragment;
import com.irmwrs.recipeapp.order.models.OrderHistoryResponse;
import com.irmwrs.recipeapp.order.views.HistoryFragment;
import com.irmwrs.recipeapp.order.views.OrderTrackerFragment;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends FragmentStateAdapter {

    List<OrderHistoryResponse> orders;
    List<OrderHistoryResponse> orderTrackerList = new ArrayList<>();
    List<OrderHistoryResponse> historyList = new ArrayList<>();

    public OrderAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<OrderHistoryResponse> orders) {
        super(fragmentManager, lifecycle);
        this.orders = orders;
        separateList();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = new Fragment();
        switch (position){
            case 0:
                fragment = new OrderTrackerFragment(orderTrackerList);
                return fragment;
            case 1:
                fragment = new HistoryFragment(historyList);
                return fragment;
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    void separateList(){
        for(int i = 0; i < orders.size(); i++){
            OrderHistoryResponse order = orders.get(i);
            if(order.status.equals("Payment Expired") || order.status.equals("Completed")){
                historyList.add(order);
            }
            else {
                orderTrackerList.add(order);
            }
        }
    }
}
