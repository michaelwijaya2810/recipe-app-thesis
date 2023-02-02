package com.irmwrs.recipeapp.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.irmwrs.recipeapp.R;

public class HistoryViewHolder extends RecyclerView.ViewHolder {
    public TextView tvOrderDate;
    public TextView tvPrice;
    public TextView tvStatus;
    public TextView tvSummary;
    public TextView TvINVCODE;

    public HistoryViewHolder(@NonNull View itemView) {
        super(itemView);

        // widget init
        tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
        tvPrice = itemView.findViewById(R.id.tvPriceOrderTracker);
        tvStatus = itemView.findViewById(R.id.tvStatus);
        tvSummary = itemView.findViewById(R.id.tvSummary);
        TvINVCODE = itemView.findViewById(R.id.TvINVCode);
    }
}
