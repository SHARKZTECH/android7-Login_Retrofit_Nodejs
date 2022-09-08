package com.example.loginnodejs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL="http://192.168.162.5:5000/api/user/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                                .build();
        retrofitInterface=retrofit.create(RetrofitInterface.class);


        findViewById(R.id.loginDial).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.registerDial).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleRegisterDialog();
            }
        });
    }

    private void handleRegisterDialog() {
        View view=getLayoutInflater().inflate(R.layout.register_dialog,null);
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setView(view).show();

        Button register=findViewById(R.id.registerDial);
        EditText name=findViewById(R.id.name);
        EditText email=findViewById(R.id.email1);
        EditText password=findViewById(R.id.password1);
        EditText password_confirm=findViewById(R.id.password2);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,String> map=new HashMap<>();
                map.put("name",name.getText().toString());
                map.put("email",email.getText().toString());
                map.put("password",password.getText().toString());
                Call<Void> call=retrofitInterface.executeRegister(map);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

//    private void handleLoginDialog() {
//        View view=getLayoutInflater().inflate(R.layout.login_dialog,null);
//        AlertDialog.Builder builder=new AlertDialog.Builder(this);
//
//        builder.setView(view);
//        final AlertDialog alertDialog=builder.create();
//
//        Button login=alertDialog.findViewById(R.id.login);
//        EditText emailInp=alertDialog.findViewById(R.id.email);
//        EditText passwordInp=alertDialog.findViewById(R.id.password);
//
//
////
////        login.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                Toast.makeText(MainActivity.this, "Login", Toast.LENGTH_SHORT).show();
//////                HashMap<String,String> map=new HashMap<>();
//////                map.put("email",emailInp.getText().toString());
//////                map.put("password",passwordInp.getText().toString());
//////                Call<LoginResults> call=retrofitInterface.excuteLogin(map);
//////
//////                call.enqueue(new Callback<LoginResults>() {
//////                    @Override
//////                    public void onResponse(Call<LoginResults> call, Response<LoginResults> response) {
//////                        Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_SHORT).show();
//////                    }
//////
//////                    @Override
//////                    public void onFailure(Call<LoginResults> call, Throwable t) {
//////                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//////                    }
//////                });
////            }
////        });
//
//        alertDialog.show();
//
//
//    }
}