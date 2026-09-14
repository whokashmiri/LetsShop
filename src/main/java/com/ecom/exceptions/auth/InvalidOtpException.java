package com.ecom.exceptions.auth;

public class InvalidOtpException extends RuntimeException{
    public InvalidOtpException(String message){
        super(message);
    };
}
