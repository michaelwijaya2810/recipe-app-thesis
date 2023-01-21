package com.irmwrs.recipeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.irmwrs.recipeapp.Class.ResponseClass.LoginResponse;
import com.irmwrs.recipeapp.Class.ResponseClass.UserResponse;
import com.irmwrs.recipeapp.Class.Validate;
import com.irmwrs.recipeapp.settings.views.SettingsFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    int userId;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            Button loginbtn = findViewById(R.id.Loginbtn);
            Server server = new Server();
            EditText usernamefield = findViewById(R.id.UsernameField);
            EditText passwordfield = findViewById(R.id.PasswordField);
            Context context = getApplicationContext();
            SharedPreferences sharedPref = context.getSharedPreferences("userinfo",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            Functions functions = new Functions(Login.this);
            TextView forgotpass = findViewById(R.id.forgotpassword);

            int Userid = 0;
            Userid = sharedPref.getInt("Userid",Userid);
            if(Userid != 0)
            {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }

            Button loginguess = findViewById(R.id.LoginGuessbtn);

            loginguess.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }
            });



        TextView registertxt = findViewById(R.id.CreateAccountTxt);

            registertxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(),Register.class);
                    startActivity(intent);
                }
            });


            loginbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                if(usernamefield.getText().toString().equals("") || passwordfield.getText().toString().equals(""))
                {
                    functions.showToast("Please Input Username and Password");
                    return;
                }

                    functions.showLoading();
                    server.postLogin(usernamefield.getText().toString(),passwordfield.getText().toString()).enqueue(new Callback<LoginResponse>(){
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if(!response.body().errorreason.equals(""))
                            {
                                Toast.makeText(getApplicationContext(),response.body().errorreason,Toast.LENGTH_SHORT).show();
                                functions.dismissLoading();
                                return;
                            }
                            else
                            {
                                userId = response.body().userid;
                                editor.putInt("Userid", response.body().userid);
                                editor.putString("Username",response.body().username);
                                server.postUserDetail(userId).enqueue(new Callback<UserResponse>() {
                                    @Override
                                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                                        if (!response.isSuccessful()) {
                                            functions.dismissLoading();
                                            functions.showToast(String.valueOf(response.code()));
                                            return;
                                        }
                                        editor.putString("Address", response.body().user.address);
                                        editor.apply();
                                        functions.dismissLoading();
                                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onFailure(Call<UserResponse> call, Throwable t) {
                                        functions.dismissLoading();
                                        functions.showToast(t.getMessage());
                                    }

                                });
                            }




                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            functions.dismissLoading();
                            functions.showToast(t.getMessage());
                        }
                    });
                }
            });


            forgotpass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    functions.showLoading();
                    if(usernamefield.getText().toString().equals(""))
                    {
                        functions.showToast("please input username");
                        functions.dismissLoading();
                        return;
                    }

                    server.getForgotPassword(usernamefield.getText().toString()).enqueue(new Callback<com.irmwrs.recipeapp.Class.ResponseClass.Response>() {
                        @Override
                        public void onResponse(Call<com.irmwrs.recipeapp.Class.ResponseClass.Response> call, Response<com.irmwrs.recipeapp.Class.ResponseClass.Response> response) {
                            if(!response.body().errorReason.equals(""))
                            {
                                functions.showToast(response.body().errorReason);
                                functions.dismissLoading();
                                return;
                            }
                            functions.showToast("please check your email");
                            functions.dismissLoading();
                            return;

                        }

                        @Override
                        public void onFailure(Call<com.irmwrs.recipeapp.Class.ResponseClass.Response> call, Throwable t) {
                        functions.dismissLoading();
                        }
                    });
                }
            });


        }




}