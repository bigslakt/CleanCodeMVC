package com.iths.robinhansson.cleancode.lab2.exceptions;

public class ProductTypeDontExistException extends IllegalArgumentException {

    public ProductTypeDontExistException(String message) {
        super(message);
    }
}
