package com.irmwrs.recipeapp.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.irmwrs.recipeapp.Class.CartItem;
import com.irmwrs.recipeapp.OnCheckListener;
import com.irmwrs.recipeapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class IngredientCartAdapter extends RecyclerView.Adapter<IngredientCartAdapter.IngredientCartViewHolder> {
    SparseBooleanArray checkedStatus = new SparseBooleanArray();
    @NonNull
    @Override
    public IngredientCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.item_row_ingredient_cart, parent, false);
        return new IngredientCartViewHolder(v, onCheckListener);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientCartViewHolder holder, int position) {
        CartItem cartItem = cartItemList.get(holder.getAdapterPosition());
        holder.cbCart.setClickable(false);
        holder.ingredientname.setText(cartItem.name);
        holder.tvIngredientPrice.setText(cartItem.getStringPrice());
        holder.etIngredientQty.setText(String.valueOf(cartItem.qty));
        Picasso.get().load(cartItem.image).into(holder.image);
        holder.bind(holder.getAdapterPosition());
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    Context ctx;
    List<CartItem> cartItemList;
    OnCheckListener onCheckListener;

    public IngredientCartAdapter(Context ctx, List<CartItem> cartItemList, OnCheckListener onCheckListener){
        this.ctx = ctx;
        this.cartItemList = cartItemList;
        this.onCheckListener = onCheckListener;
        notifyDataSetChanged();
    }

    class IngredientCartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CheckBox cbCart;
        public TextView tvIngredientPrice;
        public EditText etIngredientQty;
        public TextView ingredientname;
        OnCheckListener onCheckListener;
        public ImageView image;

        public IngredientCartViewHolder(@NonNull View itemView, OnCheckListener onCheckListener) {
            super(itemView);
            cbCart = itemView.findViewById(R.id.cbCart);
            ingredientname = itemView.findViewById(R.id.tvIngredientCartText);
            image = itemView.findViewById(R.id.ingredientCartImage);
            tvIngredientPrice = itemView.findViewById(R.id.tvIngredientPrice);
            etIngredientQty = itemView.findViewById(R.id.etIngredientQty);
            this.onCheckListener = onCheckListener;
            itemView.setOnClickListener(this);
        }

        public void bind(int position){
            cbCart.setChecked(checkedStatus.get(position, true));
            etIngredientQty.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (editable.length() != 0) {
                        int qty = Integer.parseInt(editable.toString());
                        if (qty < 1) {
                            onCheckListener.onCheckClick(getAdapterPosition(), cbCart.isChecked(), 1);
                            etIngredientQty.setText("1");
                        } else {
                            onCheckListener.onCheckClick(getAdapterPosition(), cbCart.isChecked(), qty);
                        }
                    }

                }
            });
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            if (!checkedStatus.get(adapterPosition, true)) {
                cbCart.setChecked(true);
                checkedStatus.put(adapterPosition, true);
            } else  {
                cbCart.setChecked(false);
                checkedStatus.put(adapterPosition, false);
            }
            onCheckListener.onCheckClick(getAdapterPosition(), cbCart.isChecked(), 0);
        }
    }
}

