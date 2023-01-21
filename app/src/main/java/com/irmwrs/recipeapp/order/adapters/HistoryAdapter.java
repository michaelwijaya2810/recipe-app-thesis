package com.irmwrs.recipeapp.order.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.irmwrs.recipeapp.Functions;
import com.irmwrs.recipeapp.R;
import com.irmwrs.recipeapp.order.models.OrderHistoryResponse;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>{

    Context ctx;
    List<OrderHistoryResponse> historyList;
    Functions functions;
    int deliveryFee = 10000;

    public HistoryAdapter(Context ctx, List<OrderHistoryResponse> historyList){
        this.ctx = ctx;
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.item_row_history, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {
        functions = new Functions(null);
        OrderHistoryResponse history = historyList.get(position);
        holder.tvOrderDate.setText(history.order.requestedDate);
        holder.tvPrice.setText(functions.toRupiah((double) (history.order.totalPrice + deliveryFee)));
        holder.tvStatus.setText(history.status);
        holder.TvINVCODE.setText(history.invoiceNumber);
        if(history.status.equals("Payment Expired")){
            holder.tvStatus.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.red));
        }
        else if(history.status.equals("Delivered")){
            holder.tvStatus.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.green));
        }
        holder.tvSummary.setText(history.getQtyAndNameSummary());
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderDate;
        TextView tvPrice;
        TextView tvStatus;
        TextView tvSummary;
        TextView TvINVCODE;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // widget init
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
            tvPrice = itemView.findViewById(R.id.tvPriceOrderTracker);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvSummary = itemView.findViewById(R.id.tvSummary);
            TvINVCODE = itemView.findViewById(R.id.TvINVCode);
        }
    }

    public void updateList(List<OrderHistoryResponse> historyList){
        this.historyList = historyList;
        notifyDataSetChanged();
    }
}
