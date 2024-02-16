package com.patti.exception;

public class ProductNotFoundException extends RuntimeException{

    String msg;
    public ProductNotFoundException(String msg) {
        this.msg = msg;
    }
}
