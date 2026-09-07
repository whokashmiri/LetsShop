package com.ecom.exceptions;

public class PhoneAlreadyExistsException  extends  RuntimeException{

    public PhoneAlreadyExistsException(String message){
        super(message);
    }

}
