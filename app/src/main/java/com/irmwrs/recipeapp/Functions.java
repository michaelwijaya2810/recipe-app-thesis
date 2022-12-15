package com.irmwrs.recipeapp;

import java.text.NumberFormat;
import java.util.Locale;

public class Functions {
    public String toRupiah(Double amount){
        Locale localeId = new Locale("in", "ID");
        NumberFormat format = NumberFormat.getCurrencyInstance(localeId);
        return format.format(amount);
    }
}
