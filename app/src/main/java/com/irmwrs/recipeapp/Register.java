package com.irmwrs.recipeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.irmwrs.recipeapp.Class.ResponseClass.LoginResponse;
import com.irmwrs.recipeapp.Class.UserRegister;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        EditText Userfield = findViewById(R.id.Registeruserfield);
        EditText Emailfield = findViewById(R.id.RegisterEmailField);
        EditText Passwordfield = findViewById(R.id.RegisterPasswordField);
        EditText Confirmfield = findViewById(R.id.RegisterConfirmField);
        EditText Addressfield = findViewById(R.id.RegisterAddressField);
        EditText Phonefield = findViewById(R.id.RegisterPhoneField);
        Functions functions = new Functions(this);
        Button CreateAccbtn = findViewById(R.id.createAccountBtn);
        TextView signin = findViewById(R.id.signInBtn);


        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+.";
        String alphanumeric= "^[a-zA-Z0-9._@]*$";
        CreateAccbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Userfield.getText().toString().equals("") || Userfield.getText().toString().length()<6 || Userfield.getText().toString().length()>18) {
                    Toast.makeText(getApplicationContext(),"Username field can't be empty and must be between 6-18 Character long",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!Userfield.getText().toString().matches(alphanumeric)) {
                    functions.showToast("Username Alphanumeric only");
                    return;
                }

                if(Emailfield.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Email can't be empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                if( !Patterns.EMAIL_ADDRESS.matcher(Emailfield.getText().toString()).matches()) {
                    functions.showToast("Email not Valid");
                    return;
                }
                if(Passwordfield.getText().toString().isEmpty() || Passwordfield.getText().toString().length()<6 || Passwordfield.getText().toString().length()>22) {
                    Toast.makeText(getApplicationContext(),"Password field can't be empty and must be at least 6 Character long and max 22 character",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!Passwordfield.getText().toString().matches(alphanumeric)) {
                    functions.showToast("Password Alphanumeric only");
                    return;
                }
                if(!Confirmfield.getText().toString().equals(Passwordfield.getText().toString()) )
                {
                    Toast.makeText(Register.this, "Password not Match", Toast.LENGTH_SHORT).show();
                    return;}
                if(Phonefield.getText().toString().equals("") || Phonefield.getText().toString().length()>15) {
                    Toast.makeText(Register.this, "Phone can't be empty and max 16 character", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Addressfield.getText().toString().equals("") || Addressfield.getText().toString().length()<5) {
                    Toast.makeText(Register.this, "Address can't be empty and at least 5 character", Toast.LENGTH_SHORT).show();
                    return;
                }

                Server server = new Server();
                UserRegister  userregister = new UserRegister();
                userregister.username = Userfield.getText().toString();
                userregister.email = Emailfield.getText().toString();
                userregister.password = Passwordfield.getText().toString();
                userregister.address = Addressfield.getText().toString();
                userregister.phoneNumber = Phonefield.getText().toString();
                userregister.firstName = "";
                userregister.lastName = "";

                functions.showLoading();
                server.postRegister(userregister).enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if(response.body().errorreason.matches("")) {
                            Toast.makeText(getApplicationContext(),"Register Success",Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(Register.this, response.body().errorreason, Toast.LENGTH_SHORT).show();
                            functions.dismissLoading();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {

                    }
                });

            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}