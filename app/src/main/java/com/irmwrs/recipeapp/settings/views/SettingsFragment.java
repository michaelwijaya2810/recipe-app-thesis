package com.irmwrs.recipeapp.settings.views;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.irmwrs.recipeapp.Class.ResponseClass.Response;
import com.irmwrs.recipeapp.Functions;
import com.irmwrs.recipeapp.MainActivity;
import com.irmwrs.recipeapp.R;
import com.irmwrs.recipeapp.Server;
import com.irmwrs.recipeapp.settings.models.ChangePassword;

import retrofit2.Call;
import retrofit2.Callback;

public class SettingsFragment extends Fragment {

    Button btnChangeAddress;
    EditText etAddress;
    Button btnSaveAddress;
    Button btnChangePass;
    EditText etOldPass;
    EditText etNewPass;
    Button btnSavePass;
    Button btnLogOut;

    Functions functions;
    Server server = new Server();

    int userId; //todo get user id
    String address;
    Activity activity;

    public SettingsFragment(int userId, String address, Activity activity) {
        // Required empty public constructor
        this.userId = userId;
        this.address = address;
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        functions = new Functions(activity);
        init(view);
    }

    void init(View view){
        // widget init
        btnChangeAddress = view.findViewById(R.id.btnChangeAddress);
        etAddress = view.findViewById(R.id.etAddress);
        btnSaveAddress = view.findViewById(R.id.btnSaveAddress);
        btnChangePass = view.findViewById(R.id.btnChangePass);
        etOldPass = view.findViewById(R.id.etOldPass);
        etNewPass = view.findViewById(R.id.etNewPass);
        btnSavePass = view.findViewById(R.id.btnSavePass);
        btnLogOut = view.findViewById(R.id.btnLogOut);

        // text init
        etAddress.setText(address);

        // button init
        btnChangeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etAddress.getVisibility() == View.VISIBLE){
//                    btnChangeAddress.setBackgroundTintList(ContextCompat.getColorStateList(view.getContext(), R.color.gray));
                    etAddress.setVisibility(View.GONE);
                    btnSaveAddress.setVisibility(View.GONE);
                }
                else {
                    etAddress.setVisibility(View.VISIBLE);
                    btnSaveAddress.setVisibility(View.VISIBLE);
                }
            }
        });
        btnSaveAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etAddress.getText().toString().equals("")){
                    functions.showToast("Address can't be empty!");
                }
                else {
                    // todo call api
                }
            }
        });
        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etOldPass.getVisibility() == View.VISIBLE){
                    etOldPass.setVisibility(View.GONE);
                    etNewPass.setVisibility(View.GONE);
                    btnSavePass.setVisibility(View.GONE);
                }
                else {
                    etOldPass.setVisibility(View.VISIBLE);
                    etNewPass.setVisibility(View.VISIBLE);
                    btnSavePass.setVisibility(View.VISIBLE);
                }
            }
        });
        btnSavePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etOldPass.getText().toString().equals("")){
                    functions.showToast("Old password can't be empty");
                }
                else if (etNewPass.getText().toString().equals("")){
                    functions.showToast("New password can't be empty");
                }
                else {
                    ChangePassword changePassword = new ChangePassword();
                    changePassword.newPassword = etNewPass.getText().toString();
                    changePassword.oldPassword = etOldPass.getText().toString();
                    changePassword.userid = String.valueOf(userId);
                    functions.showLoading();
                    server.postChangePassword(changePassword).enqueue(new Callback<Response>() {
                        @Override
                        public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                            if (!response.isSuccessful()){
                                functions.dismissLoading();
                                functions.showToast(String.valueOf(response.code()));
                                return;
                            }
                            if(response.body().errorReason != null){
                                functions.dismissLoading();
                                functions.showToast(response.body().errorReason);
                                return;
                            }
                            functions.dismissLoading();
                            functions.showToast("Password changed successfully!");
                        }

                        @Override
                        public void onFailure(Call<Response> call, Throwable t) {
                            functions.showToast(t.getMessage());
                        }
                    });
                }
            }
        });
    }
}