package com.irmwrs.recipeapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.irmwrs.recipeapp.R;
import com.irmwrs.recipeapp.viewholders.SummaryViewHolder;

import java.util.ArrayList;

public class SummaryListAdapter extends RecyclerView.Adapter<SummaryViewHolder>{
    ArrayList<String> qtyNameList;
    ArrayList<String> priceList;

    public SummaryListAdapter(ArrayList<String> qtyNameList, ArrayList<String> priceList) {
        this.qtyNameList = qtyNameList;
        this.priceList = priceList;
    }

    @NonNull
    @Override
    public SummaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_summary, parent, false);
        return new SummaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SummaryViewHolder holder, int position) {
        holder.tvQtyAndName.setText(qtyNameList.get(position));
        holder.tvPrice.setText(priceList.get(position));
    }

    @Override
    public int getItemCount() {
        return qtyNameList.size();
    }
}
