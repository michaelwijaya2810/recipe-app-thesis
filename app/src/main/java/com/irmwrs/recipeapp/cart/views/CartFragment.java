package com.irmwrs.recipeapp.cart.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.irmwrs.recipeapp.Class.ResponseClass.Response;
import com.irmwrs.recipeapp.Functions;
import com.irmwrs.recipeapp.OnCheckListener;
import com.irmwrs.recipeapp.Server;
import com.irmwrs.recipeapp.cart.models.CartOrderResponse;
import com.irmwrs.recipeapp.cart.adapters.CartAdapter;
import com.irmwrs.recipeapp.payment.views.PaymentActivity;
import com.irmwrs.recipeapp.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class CartFragment extends Fragment implements OnCheckListener {

    TextView tvSelected;
    RecyclerView rvCart;
    TextView tvTotalPriceTitle;
    TextView tvTotalPrice;

    TextView noItemTitle;
    TextView noItemSubtitle;

    Button btnCheckout;

    CartAdapter adapter;
    LinearLayoutManager linearLayoutManager;

    List<CartOrderResponse> cartItems;
    Activity activity;
    Functions functions;
    List<CartOrderResponse> selected = new ArrayList<>();
    final int deliveryFee = 10000;
    double totalPrice;

    public CartFragment(List<CartOrderResponse> cartItems, Activity activity) {
        this.cartItems = cartItems;
        this.activity = activity;
        selected.addAll(cartItems);
        Collections.reverse(cartItems);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        functions = new Functions(activity);
        init(view);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rvCart);

    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            Server server = new Server();
            int position = viewHolder.getAdapterPosition();
            if(direction == ItemTouchHelper.RIGHT)
            {
                functions.showLoading();
                server.RemoveOrder(cartItems.get(position).orderId).enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        cartItems.remove(position);
                        adapter.notifyDataSetChanged();
                        selected.remove(position);
                        getTotalPrice();
                        updateText();
                        functions.dismissLoading();
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {
                        cartItems.remove(position);
                        adapter.notifyDataSetChanged();
                        selected.remove(position);
                        getTotalPrice();
                        updateText();
                        functions.dismissLoading();
                        functions.dismissLoading();
                    }
                });

            }

        }
    };

    void init(View view){
        // widgets init
        tvSelected = view.findViewById(R.id.tvSelected);
        rvCart = view.findViewById(R.id.rvCart);
        tvTotalPriceTitle = view.findViewById(R.id.tvTotalPriceTitle);
        tvTotalPrice = view.findViewById(R.id.tvTotalPrice);

        noItemTitle = view.findViewById(R.id.no_item_title);
        noItemSubtitle = view.findViewById(R.id.no_item_subtitle);

        btnCheckout = view.findViewById(R.id.btnOrder);

        // buttons init
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selected.size() == 0){
                    functions.showToast("Please select an item to checkout");
                }
                else {
                    ArrayList<Integer> orderList = new ArrayList<>();
                    for (int i = 0; i < selected.size(); i++){
                        orderList.add((int) selected.get(i).orderId);
                    }
                    Intent intent = new Intent(getContext(), PaymentActivity.class);
                    intent.putExtra("total_price", totalPrice + deliveryFee);
                    intent.putIntegerArrayListExtra("order_list", orderList);
                    startActivity(intent);
                }
            }
        });

        // recyclerview init
        linearLayoutManager = new LinearLayoutManager(getContext());



        adapter = new CartAdapter(getContext(), cartItems, this);
        rvCart.setLayoutManager(linearLayoutManager);
        rvCart.setAdapter(adapter);

        // texts init
        updateText();
    }

    void updateText(){
        if (cartItems.isEmpty()) {
            tvSelected.setVisibility(View.GONE);
            rvCart.setVisibility(View.GONE);
            btnCheckout.setVisibility(View.GONE);
            tvTotalPriceTitle.setVisibility(View.GONE);
            tvTotalPrice.setVisibility(View.GONE);

            noItemTitle.setVisibility(View.VISIBLE);
            noItemSubtitle.setVisibility(View.VISIBLE);
        } else {
            tvSelected.setVisibility(View.VISIBLE);
            rvCart.setVisibility(View.VISIBLE);
            btnCheckout.setVisibility(View.VISIBLE);

            noItemTitle.setVisibility(View.GONE);
            noItemSubtitle.setVisibility(View.GONE);

            String selectedItems = selected.size() + "/" + cartItems.size() + " selected items";
            tvSelected.setText(selectedItems);

            if (selected.isEmpty()) {
                tvTotalPriceTitle.setVisibility(View.GONE);
                tvTotalPrice.setVisibility(View.GONE);
            } else {
                tvTotalPriceTitle.setVisibility(View.VISIBLE);
                tvTotalPrice.setVisibility(View.VISIBLE);
                String totalPrice = getTotalPrice();
                tvTotalPrice.setText(totalPrice);
            }
        }
    }

    String getTotalPrice(){
        totalPrice = 0;
        for (int i = 0; i < selected.size(); i++){
            totalPrice = totalPrice + selected.get(i).totalPrice;
        }
        Functions functions = new Functions(null);
        return functions.toRupiah(totalPrice) + " + " + functions.toRupiah((double) deliveryFee);
    }

    @Override
    public void onCheckClick(int position, boolean isChecked, int qty) {
        if(isChecked){
            selected.add(cartItems.get(position));
        }
        else {
            selected.remove(cartItems.get(position));
        }
        updateText();
    }
}