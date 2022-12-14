package com.irmwrs.recipeapp.Class;

public class CartItem {
    public String name;
    public String image;
    public double price;
    public int qty;
    public long ingredientId;

    public String getStringPrice(){
        return "Rp. " + price;
    }
}
