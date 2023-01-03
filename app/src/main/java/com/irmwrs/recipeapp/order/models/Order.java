package com.irmwrs.recipeapp.order.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {

    @SerializedName("Id")
    @Expose
    public long id;
    @SerializedName("CustomerID")
    @Expose
    public int customerID;
    @SerializedName("StatusID")
    @Expose
    public int statusID;
    @SerializedName("IsActive")
    @Expose
    public boolean isActive;
    @SerializedName("DeletedBy")
    @Expose
    public int deletedBy;
    @SerializedName("ModifiedBy")
    @Expose
    public int modifiedBy;
    @SerializedName("DeletedDate")
    @Expose
    public String deletedDate;
    @SerializedName("ModifiedDate")
    @Expose
    public String modifiedDate;
    @SerializedName("RequestedDate")
    @Expose
    public String requestedDate;
    @SerializedName("CreatedDate")
    @Expose
    public String createdDate;
    @SerializedName("TotalPrice")
    @Expose
    public int totalPrice;
    @SerializedName("RecipeId")
    @Expose
    public long recipeId;
}
