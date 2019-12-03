package com.iths.robinhansson.cleancode.lab2.exceptions;

public class ProductColourDontExistException extends IllegalArgumentException {

    public ProductColourDontExistException(String message) {
        super(message);
    }
}
