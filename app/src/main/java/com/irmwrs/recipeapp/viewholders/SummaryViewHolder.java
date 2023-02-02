package com.irmwrs.recipeapp.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.irmwrs.recipeapp.R;

public class SummaryViewHolder extends RecyclerView.ViewHolder{
    public TextView tvQtyAndName;
    public TextView tvPrice;

    public SummaryViewHolder(@NonNull View itemView) {
        super(itemView);
        tvQtyAndName = itemView.findViewById(R.id.tvQtyAndName);
        tvPrice = itemView.findViewById(R.id.tvPrice);
    }

}
