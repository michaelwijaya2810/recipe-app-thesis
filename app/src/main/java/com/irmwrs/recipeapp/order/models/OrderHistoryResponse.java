package com.irmwrs.recipeapp.order.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.irmwrs.recipeapp.order.models.Order;
import com.irmwrs.recipeapp.order.models.OrderDetail;

public class OrderHistoryResponse {

    @SerializedName("Order")
    @Expose
    public Order order;
    @SerializedName("InvoiceNumber")
    @Expose
    public String invoiceNumber;
    @SerializedName("RecipeName")
    @Expose
    public String recipeName;
    @SerializedName("Status")
    @Expose
    public String status;
    @SerializedName("VirtualNumber")
    @Expose
    public String virtualNumber;
    @SerializedName("RecipeImage")
    @Expose
    public String recipeImage;
    @SerializedName("OrderDetail")
    @Expose
    public List<OrderDetail> orderDetail = null;

    public String getQtyAndNameSummary(){
        String qty_name = "";
        for (int i = 0; i < orderDetail.size(); i++){
            qty_name += orderDetail.get(i).ingredientQty + "x " + orderDetail.get(i).ingredientName + "\n";
        }
        return qty_name;
    }
}
