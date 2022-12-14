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

    public CartAdapter( Context ctx, List<CartOrderResponse> cartItems){
        this.ctx = ctx;
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.item_row_cart, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartOrderResponse curr = cartItems.get(position);
        holder.cbRecipeName.setText(curr.recipeName);
        holder.tvQtyAndName.setText(curr.getQtyAndNameSummary());
        holder.tvPrice.setText(curr.getPriceSummary());
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox cbRecipeName;
        TextView tvQtyAndName;
        TextView tvPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cbRecipeName = itemView.findViewById(R.id.cbCart);
            tvQtyAndName = itemView.findViewById(R.id.tvQtyAndName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }
    }
}
