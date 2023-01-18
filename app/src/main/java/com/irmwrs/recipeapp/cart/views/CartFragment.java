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
import com.irmwrs.recipeapp.Server;
import com.irmwrs.recipeapp.cart.models.CartOrderResponse;
import com.irmwrs.recipeapp.cart.adapters.CartAdapter;
import com.irmwrs.recipeapp.payment.views.PaymentActivity;
import com.irmwrs.recipeapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class CartFragment extends Fragment implements CartAdapter.ViewHolder.OnCheckListener {

    TextView tvSelected;
    RecyclerView rvCart;
    TextView tvTotalPrice;
    Button btnCheckout;

    CartAdapter adapter;
    LinearLayoutManager linearLayoutManager;

    List<CartOrderResponse> cartItems;
    Activity activity;
    Functions functions;
    List<CartOrderResponse> selected = new ArrayList<>();
    int deliveryFee = 10000;
    double totalPrice;

    public CartFragment(List<CartOrderResponse> cartItems, Activity activity) {
        this.cartItems = cartItems;
        this.activity = activity;
        selected.addAll(cartItems);
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
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {

                    }
                });
                selected.remove(position);
                getTotalPrice();
                updateText();
                functions.dismissLoading();

            }

        }
    };

    void init(View view){
        // widgets init
        tvSelected = view.findViewById(R.id.tvSelected);
        rvCart = view.findViewById(R.id.rvCart);
        tvTotalPrice = view.findViewById(R.id.tvTotalPrice);
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
        String selectedItems = selected.size() + "/" + cartItems.size() + " selected items";
        tvSelected.setText(selectedItems);
        String totalPrice = "Total price + Delivery Fee\n" + getTotalPrice();
        tvTotalPrice.setText(totalPrice);
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
    public void onCheckClick(int position, boolean isChecked) {
        if(isChecked){
            selected.add(cartItems.get(position));
        }
        else {
            selected.remove(cartItems.get(position));
        }
        updateText();
    }
}