package com.irmwrs.recipeapp.cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.irmwrs.recipeapp.IngredientCartAdapter;
import com.irmwrs.recipeapp.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    Context ctx;
    List<CartOrderResponse> cartItems;
    ViewHolder.OnCheckListener onCheckListener;

    public CartAdapter(Context ctx, List<CartOrderResponse> cartItems, ViewHolder.OnCheckListener onCheckListener){
        this.ctx = ctx;
        this.cartItems = cartItems;
        this.onCheckListener = onCheckListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.item_row_cart, parent, false);
        return new ViewHolder(v, onCheckListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartOrderResponse curr = cartItems.get(position);
        holder.cbRecipeName.setChecked(true);
        holder.cbRecipeName.setClickable(false); // clickable function already managed by ViewHolder
        holder.cbRecipeName.setText(curr.recipeName);
        holder.tvQtyAndName.setText(curr.getQtyAndNameSummary());
        holder.tvPrice.setText(curr.getPriceSummary());
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CheckBox cbRecipeName;
        TextView tvQtyAndName;
        TextView tvPrice;
        OnCheckListener onCheckListener;

        public ViewHolder(@NonNull View itemView, OnCheckListener onCheckListener) {
            super(itemView);
            // widget init
            cbRecipeName = itemView.findViewById(R.id.cbCart);
            tvQtyAndName = itemView.findViewById(R.id.tvQtyAndName);
            tvPrice = itemView.findViewById(R.id.tvPrice);

            // onclick init
            this.onCheckListener = onCheckListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(cbRecipeName.isChecked()){
                cbRecipeName.setChecked(false);
            }
            else {
                cbRecipeName.setChecked(true);
            }
            onCheckListener.onCheckClick(getAdapterPosition(), cbRecipeName.isChecked());
        }

        public interface OnCheckListener{
            void onCheckClick(int position, boolean isChecked);
        }
    }
}
