package com.irmwrs.recipeapp.order.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.irmwrs.recipeapp.order.models.OrderHistoryResponse;

import java.util.List;

public class OrderAdapter extends FragmentStateAdapter {

    List<OrderHistoryResponse> orders;

    public OrderAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<OrderHistoryResponse> orders) {
        super(fragmentManager, lifecycle);
        this.orders = orders;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return null;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
