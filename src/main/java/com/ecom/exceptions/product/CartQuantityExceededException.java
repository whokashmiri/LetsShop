package com.ecom.exceptions.product;

public class CartQuantityExceededException extends RuntimeException{
    public CartQuantityExceededException(String message){
        super(message);
    }
}
