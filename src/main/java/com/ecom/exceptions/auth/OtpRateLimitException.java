package com.ecom.exceptions.auth;

public class OtpRateLimitException extends RuntimeException{
    public OtpRateLimitException(String message){
        super(message);
    }
}
