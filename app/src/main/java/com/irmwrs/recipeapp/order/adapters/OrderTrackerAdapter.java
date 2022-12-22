package com.irmwrs.recipeapp.order.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.irmwrs.recipeapp.Functions;
import com.irmwrs.recipeapp.R;
import com.irmwrs.recipeapp.order.models.Order;
import com.irmwrs.recipeapp.order.models.OrderHistoryResponse;

import java.util.List;

public class OrderTrackerAdapter extends RecyclerView.Adapter<OrderTrackerAdapter.ViewHolder> {

    Context ctx;
    List<OrderHistoryResponse> orderTrackerList;
    OnOrderTrackerListener onOrderTrackerListener;

    Functions functions;
    int deliveryFee = 10000;

    public OrderTrackerAdapter(Context ctx, List<OrderHistoryResponse> orderTrackerList, OnOrderTrackerListener onOrderTrackerListener){
        this.ctx = ctx;
        this.orderTrackerList = orderTrackerList;
        this.onOrderTrackerListener = onOrderTrackerListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.item_row_order_tracker, parent, false);
        return new ViewHolder(v, onOrderTrackerListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        functions = new Functions(null);
        OrderHistoryResponse orderTracker = orderTrackerList.get(position);
        holder.tvOrderDate.setText(orderTracker.order.requestedDate);
        holder.tvPrice.setText(functions.toRupiah((double) (orderTracker.order.totalPrice + deliveryFee)));
        holder.tvStatus.setText(orderTracker.status);
        holder.tvSummary.setText(orderTracker.getQtyAndNameSummary());
    }

    @Override
    public int getItemCount() {
        return orderTrackerList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvOrderDate;
        TextView tvPrice;
        TextView tvStatus;
        TextView tvSummary;
        OnOrderTrackerListener onOrderTrackerListener;
        public ViewHolder(@NonNull View itemView, OnOrderTrackerListener onOrderTrackerListener) {
            super(itemView);

            // widget init
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
            tvPrice = itemView.findViewById(R.id.tvPriceOrderTracker);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvSummary = itemView.findViewById(R.id.tvSummary);

            this.onOrderTrackerListener = onOrderTrackerListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onOrderTrackerListener.onOrderTrackerClick(getAdapterPosition());
        }
    }

    public interface OnOrderTrackerListener{
        void onOrderTrackerClick(int position);
    }
}
