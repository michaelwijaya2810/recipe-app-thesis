package com.irmwrs.recipeapp;

// sample class
public class Ingredient {
    String name;
    int image;
    double price;
    int qty;

    public String getStringPrice(){
        return "Rp. " + price;
    }
}
