package com.ecom.dto;

public class LoginRequest {
    private String  phone;
    private String password;

    public void setPhone(String phone){
        this.phone = phone;
    }
    public String getPhone(){
        return phone;
    }
    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
