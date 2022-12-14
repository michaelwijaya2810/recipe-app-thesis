package com.irmwrs.recipeapp.cart;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartOrderResponse {
    @SerializedName("OrderId")
    @Expose
    public long orderId;
    @SerializedName("RecipeName")
    @Expose
    public String recipeName;
    @SerializedName("RecipeImage")
    @Expose
    public String recipeImage;
    @SerializedName("TotalPrice")
    @Expose
    public int totalPrice;
    @SerializedName("OrderDetail")
    @Expose
    public List<OrderDetail> orderDetail = null;

    public String getQtyAndNameSummary(){
        String qty_name = "";
        for (int i = 0; i < orderDetail.size(); i++){
            qty_name += orderDetail.get(i).ingredientQty + "x " + orderDetail.get(i).ingredientName + "\n";
        }
        qty_name += "\n" + "Total price";
        return qty_name;
    }

    public String getPriceSummary(){
        String price = "";
        for (int i = 0; i < orderDetail.size(); i++){
            price += "Rp" + orderDetail.get(i).ingredientPrice + "\n";
        }
        price += "\n" + "Rp" + totalPrice;
        return price;
    }
}
