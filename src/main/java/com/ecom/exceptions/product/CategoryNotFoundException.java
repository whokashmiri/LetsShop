package com.ecom.exceptions.product;

public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException (String message){
        super(message);
    }
}
