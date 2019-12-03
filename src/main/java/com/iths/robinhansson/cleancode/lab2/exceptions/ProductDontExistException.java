package com.iths.robinhansson.cleancode.lab2.exceptions;

public class ProductDontExistException extends NullPointerException {

    public ProductDontExistException(String message) {
        super(message);
    }

}
