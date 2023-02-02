package com.irmwrs.recipeapp.cart.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.irmwrs.recipeapp.Login;
import com.irmwrs.recipeapp.OnCheckListener;
import com.irmwrs.recipeapp.R;
import com.irmwrs.recipeapp.cart.models.CartOrderResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    SparseBooleanArray checkedStatus = new SparseBooleanArray();
    Context ctx;
    List<CartOrderResponse> cartItems;
    OnCheckListener onCheckListener;

    ArrayList<String> qtyNameList;
    ArrayList<String> priceList;

    public CartAdapter(Context ctx, List<CartOrderResponse> cartItems, OnCheckListener onCheckListener){
        this.ctx = ctx;
        this.cartItems = cartItems;
        this.onCheckListener = onCheckListener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.item_row_cart, parent, false);
        long userId;
        Context context = parent.getContext();
        SharedPreferences sharepref = context.getSharedPreferences("userinfo",Context.MODE_PRIVATE);

        userId = sharepref.getInt("Userid",0);
        if(userId == 0)
        {
            Toast.makeText(context, "Invalid Login Session", Toast.LENGTH_SHORT).show();

            Intent intentlogin = new Intent(context, Login.class);
            context.startActivity(intentlogin);

        }
        return new CartViewHolder(v, onCheckListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartOrderResponse curr = cartItems.get(position);
        holder.cbRecipeName.setClickable(false); // clickable function already managed by ViewHolder
        holder.cbRecipeName.setText(curr.recipeName);

        String[] qtyName = curr.getQtyAndNameSummary().split("\n");
        String[] prices = curr.getPriceSummary().split("\n");
        qtyNameList = new ArrayList<>(Arrays.asList(qtyName));
        priceList = new ArrayList<>(Arrays.asList(prices));

        for (int i = 0; i < qtyNameList.size(); i++) {
            LinearLayout itemParent = new LinearLayout(ctx);
            itemParent.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            itemParent.setOrientation(LinearLayout.HORIZONTAL);
            itemParent.setPadding(4, 4, 4, 4);

            TextView qtyNameItem = new TextView(ctx);
            qtyNameItem.setText(qtyNameList.get(i));
            qtyNameItem.setLayoutParams(new TableLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    1.25f));
            qtyNameItem.setTextColor(ctx.getColor(R.color.black));
            qtyNameItem.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            qtyNameItem.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);

            TextView priceItem = new TextView(ctx);
            priceItem.setText(priceList.get(i));
            priceItem.setLayoutParams(new TableLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    2f));
            priceItem.setTextColor(ctx.getColor(R.color.black));
            priceItem.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            priceItem.setTypeface(null, Typeface.BOLD);
            priceItem.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);

            itemParent.addView(qtyNameItem);
            itemParent.addView(priceItem);

            holder.itemList.addView(itemParent);
        }
        holder.bind(holder.getAdapterPosition());
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CheckBox cbRecipeName;
        public LinearLayout itemList;
        OnCheckListener onCheckListener;

        public CartViewHolder(@NonNull View itemView, OnCheckListener onCheckListener) {
            super(itemView);
            // widget init
            cbRecipeName = itemView.findViewById(R.id.cbCart);
            // ingredient item list layout
            itemList = itemView.findViewById(R.id.item_list_layout);
            // onclick init
            this.onCheckListener = onCheckListener;
            itemView.setOnClickListener(this);
        }

        public void bind(int position) {
            cbRecipeName.setChecked(checkedStatus.get(position, true));
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            if (!checkedStatus.get(adapterPosition, true)) {
                cbRecipeName.setChecked(true);
                checkedStatus.put(adapterPosition, true);
            }
            else  {
                cbRecipeName.setChecked(false);
                checkedStatus.put(adapterPosition, false);
            }
            onCheckListener.onCheckClick(getAdapterPosition(), cbRecipeName.isChecked(), 0);
        }
    }
}
