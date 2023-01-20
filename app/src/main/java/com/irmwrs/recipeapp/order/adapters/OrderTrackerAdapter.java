package com.irmwrs.recipeapp.order.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.irmwrs.recipeapp.Class.ResponseClass.Response;
import com.irmwrs.recipeapp.Functions;
import com.irmwrs.recipeapp.R;
import com.irmwrs.recipeapp.Server;
import com.irmwrs.recipeapp.order.models.Order;
import com.irmwrs.recipeapp.order.models.OrderHistoryResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class OrderTrackerAdapter extends RecyclerView.Adapter<OrderTrackerAdapter.ViewHolder> {

    Context ctx;
    List<OrderHistoryResponse> orderTrackerList;
    OnOrderTrackerListener onOrderTrackerListener;
    Activity activity;

    Functions functions;
    Server server;
    int deliveryFee = 10000;

    public OrderTrackerAdapter(Context ctx, List<OrderHistoryResponse> orderTrackerList, OnOrderTrackerListener onOrderTrackerListener, Activity activity){
        this.ctx = ctx;
        this.orderTrackerList = orderTrackerList;
        this.onOrderTrackerListener = onOrderTrackerListener;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.item_row_order_tracker, parent, false);
        return new ViewHolder(v, onOrderTrackerListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        functions = new Functions(activity);
        OrderHistoryResponse orderTracker = orderTrackerList.get(position);
        holder.tvOrderDate.setText(orderTracker.order.requestedDate);
        holder.tvPrice.setText(functions.toRupiah((double) (orderTracker.order.totalPrice + deliveryFee)));
        holder.tvStatus.setText(orderTracker.status);
        holder.tvSummary.setText(orderTracker.getQtyAndNameSummary());
        if(orderTracker.status.equals("Delivering")){
            holder.btnConfirmDelivery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    confirmDelivery(orderTracker, holder);
                }
            });
            holder.btnConfirmDelivery.setVisibility(View.VISIBLE);
        }
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
        Button btnConfirmDelivery;
        OnOrderTrackerListener onOrderTrackerListener;
        public ViewHolder(@NonNull View itemView, OnOrderTrackerListener onOrderTrackerListener) {
            super(itemView);

            // widget init
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
            tvPrice = itemView.findViewById(R.id.tvPriceOrderTracker);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvSummary = itemView.findViewById(R.id.tvSummary);
            btnConfirmDelivery = itemView.findViewById(R.id.btnConfirmDelivery);

            this.onOrderTrackerListener = onOrderTrackerListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onOrderTrackerListener.onOrderTrackerClick(getAdapterPosition(), false);
        }
    }

    public interface OnOrderTrackerListener{
        void onOrderTrackerClick(int position, boolean isDelivered);
    }

    void confirmDelivery(OrderHistoryResponse orderTracker, ViewHolder holder){
        server = new Server();
        functions.showLoading();
        server.getConfirmDelivery(orderTracker.order.customerID, String.valueOf(orderTracker.order.id)).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                functions.dismissLoading();
                functions.showToast("Order Complete!");
                onOrderTrackerListener.onOrderTrackerClick(0, true);
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                functions.dismissLoading();
                functions.showToast("Order Complete!");
                onOrderTrackerListener.onOrderTrackerClick(0, true);
            }
        });
    }

}
