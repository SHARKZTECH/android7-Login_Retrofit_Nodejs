package com.example.loginnodejs;

import com.google.gson.annotations.SerializedName;

public class LoginResults {
    private String token;
    private String id;
    private  String name;
    private  String email;

    public String getToken() {
        return token;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }


}
