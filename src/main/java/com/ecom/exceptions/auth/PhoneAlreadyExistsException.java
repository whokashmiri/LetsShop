package com.ecom.exceptions.auth;

public class PhoneAlreadyExistsException  extends  RuntimeException{

    public PhoneAlreadyExistsException(String message){
        super(message);
    }

}
