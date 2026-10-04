package com.ecom.exceptions.auth;

public class OtpCoolDownException extends RuntimeException{
    public OtpCoolDownException(String message){
        super(message);
    }
}
