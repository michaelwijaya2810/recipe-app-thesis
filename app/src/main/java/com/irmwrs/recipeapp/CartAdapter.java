package com.irmwrs.recipeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.item_row_cart, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ingredient ingredient = ingredientList.get(position);
        holder.ivIngredient.setImageResource(ingredient.image);
        holder.tvIngredient.setText(ingredient.name);
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    Context ctx;
    List<Ingredient> ingredientList;

    public CartAdapter(Context ctx, List<Ingredient> ingredientList){
        this.ctx = ctx;
        this.ingredientList = ingredientList;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivIngredient;
        TextView tvIngredient;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivIngredient = itemView.findViewById(R.id.ivIngredient);
            tvIngredient = itemView.findViewById(R.id.tvIngredient);
        }
    }
}

