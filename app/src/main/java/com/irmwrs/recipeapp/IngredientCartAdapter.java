package com.irmwrs.recipeapp;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.irmwrs.recipeapp.Class.CartItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class IngredientCartAdapter extends RecyclerView.Adapter<IngredientCartAdapter.ViewHolder> {
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.item_row_ingredient_cart, parent, false);
        return new ViewHolder(v, onCheckListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItem cartItem = cartItemList.get(position);
        holder.ingredientname.setText(cartItem.name);
        holder.tvIngredientPrice.setText(cartItem.getStringPrice());
        holder.etIngredientQty.setText(String.valueOf(cartItem.qty));
        Picasso.get().load(cartItem.image).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    Context ctx;
    List<CartItem> cartItemList;
    ViewHolder.OnCheckListener onCheckListener;

    public IngredientCartAdapter(Context ctx, List<CartItem> cartItemList, ViewHolder.OnCheckListener onCheckListener){
        this.ctx = ctx;
        this.cartItemList = cartItemList;
        this.onCheckListener = onCheckListener;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CheckBox cbCart;
        TextView tvIngredientPrice;
        EditText etIngredientQty;
        TextView ingredientname;
        OnCheckListener onCheckListener;
        ImageView image;

        public ViewHolder(@NonNull View itemView, OnCheckListener onCheckListener) {
            super(itemView);
            cbCart = itemView.findViewById(R.id.cbCart);
            ingredientname = itemView.findViewById(R.id.Ingredientcarttext);
            image = itemView.findViewById(R.id.Ingredientccartimage);

            cbCart.setChecked(true);
            cbCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onCheckListener.onCheckClick(getAdapterPosition(), cbCart.isChecked(), 0);
                }
            });
            tvIngredientPrice = itemView.findViewById(R.id.tvIngredientPrice);
            etIngredientQty = itemView.findViewById(R.id.etIngredientQty);
            etIngredientQty.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (editable.length() != 0){
                        int qty = Integer.parseInt(editable.toString());
                        if(qty < 1){
                            onCheckListener.onCheckClick(getAdapterPosition(), cbCart.isChecked(), 1);
                            etIngredientQty.setText("1");
                        }
                        else {
                            onCheckListener.onCheckClick(getAdapterPosition(), cbCart.isChecked(), qty);
                        }
                    }

                }
            });
            this.onCheckListener = onCheckListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(cbCart.isChecked()){
                cbCart.setChecked(false);
            }
            else {
                cbCart.setChecked(true);
            }
            onCheckListener.onCheckClick(getAdapterPosition(), cbCart.isChecked(), 0);
        }

        public interface OnCheckListener{
            void onCheckClick(int position, boolean isChecked, int qty);
        }
    }
}

