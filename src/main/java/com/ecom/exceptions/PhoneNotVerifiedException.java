package com.ecom.exceptions;

public class PhoneNotVerifiedException extends RuntimeException{
    public PhoneNotVerifiedException (String message){
        super(message);
    }
}
