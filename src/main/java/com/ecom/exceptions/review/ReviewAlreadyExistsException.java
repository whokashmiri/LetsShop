package com.ecom.exceptions.review;

public class ReviewAlreadyExistsException extends RuntimeException{
    public ReviewAlreadyExistsException(String message){
        super(message);
    }
}
