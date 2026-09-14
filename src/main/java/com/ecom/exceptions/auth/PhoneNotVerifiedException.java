package com.ecom.exceptions.auth;

public class PhoneNotVerifiedException extends RuntimeException{
    public PhoneNotVerifiedException (String message){
        super(message);
    }
}
