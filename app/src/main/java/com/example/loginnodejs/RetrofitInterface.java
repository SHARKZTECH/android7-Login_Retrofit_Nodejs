package com.example.loginnodejs;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitInterface {
    @POST("/login")
    Call<LoginResults> excuteLogin(@Body HashMap<String,String> map);

    @POST("/register")
    Call<Void> executeRegister (@Body HashMap<String,String> map);
}
