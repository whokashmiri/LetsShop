package com.ecom.dto.auth;

public class SendOtpRequest {
  private   String phone;

    public void setPhone( String phone){
        this.phone = phone;
    }
    public String getPhone(){
        return phone;
    }
}
