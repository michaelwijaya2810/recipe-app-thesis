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
import com.irmwrs.recipeapp.Class.Validate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {


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
                    server.postLogin(usernamefield.getText().toString(),passwordfield.getText().toString()).enqueue(new Callback<LoginResponse>(){
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            if(response.body().errorreason != null)
                            {
                                Toast.makeText(getApplicationContext(),response.body().errorreason,Toast.LENGTH_SHORT).show();
                            }
                            else
                            {

                                editor.putInt("Userid", response.body().userid);
                                editor.putString("Username",response.body().username);
                                editor.apply();
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                            }

                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {

                        }
                    });
                }
            });


        }
}