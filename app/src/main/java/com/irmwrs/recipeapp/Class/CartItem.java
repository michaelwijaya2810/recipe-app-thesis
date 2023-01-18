package com.irmwrs.recipeapp.Class;

import com.irmwrs.recipeapp.Functions;

import java.text.NumberFormat;
import java.util.Locale;

public class CartItem {
    public String name;
    public String image;
    public double price;
    public String uom;
    public int qty;
    public long ingredientId;

    public String getStringPrice(){
        Locale localeId = new Locale("in", "ID");
        NumberFormat format = NumberFormat.getCurrencyInstance(localeId);
        return format.format(price);
    }
}
