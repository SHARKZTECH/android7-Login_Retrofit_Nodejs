package com.example.loginnodejs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class LoginActivity extends AppCompatActivity {
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL="http://192.168.162.5:5000/api/user/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitInterface=retrofit.create(RetrofitInterface.class);

        Button login=findViewById(R.id.login);
        EditText emailInp=findViewById(R.id.email);
        EditText passwordInp=findViewById(R.id.password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,String> map=new HashMap<>();
                map.put("email",emailInp.getText().toString());
                map.put("password",passwordInp.getText().toString());

//                Toast.makeText(LoginActivity.this, map.toString(), Toast.LENGTH_SHORT).show();
                Call<LoginResults> call=retrofitInterface.excuteLogin(map);
                call.enqueue(new Callback<LoginResults>() {
                    @Override
                    public void onResponse(Call<LoginResults> call, Response<LoginResults> response) {
                    if(response.isSuccessful()){
                        LoginResults results=response.body();
                        Toast.makeText(LoginActivity.this, results.getName(), Toast.LENGTH_SHORT).show();
                    }
                    if(response.code() == 400){
                        Toast.makeText(LoginActivity.this,"Incorrect Username or Password", Toast.LENGTH_SHORT).show();
                    }else{
                            Toast.makeText(LoginActivity.this,"Login successfully!", Toast.LENGTH_SHORT).show();
                        Log.d("User",response.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResults> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}