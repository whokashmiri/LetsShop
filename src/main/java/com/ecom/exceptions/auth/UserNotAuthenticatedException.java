package com.ecom.exceptions.auth;

public class UserNotAuthenticatedException extends  RuntimeException{
    public  UserNotAuthenticatedException (String message){
        super(message);
    }
}
