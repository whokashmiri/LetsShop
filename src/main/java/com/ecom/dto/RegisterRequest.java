package com.ecom.dto;

public class RegisterRequest {
    String name;
    String email;
    String phone;
    String password;

    public String getEmail(){
        return email;
    }

    public String getPhone(){
        return phone;
    }
}
