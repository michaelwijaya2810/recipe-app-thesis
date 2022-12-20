package com.irmwrs.recipeapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

public class Functions {
    AlertDialog dialog;
    public String toRupiah(Double amount){
        Locale localeId = new Locale("in", "ID");
        NumberFormat format = NumberFormat.getCurrencyInstance(localeId);
        return format.format(amount);
    }
    public void showToast(Context ctx, String msg){
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }
    public void showLoading(Activity activity){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_dialog, null));
        builder.setCancelable(false);
        dialog = builder.create();
        dialog.show();
    }
    public void dismissLoading(){
        dialog.dismiss();
    }
}
