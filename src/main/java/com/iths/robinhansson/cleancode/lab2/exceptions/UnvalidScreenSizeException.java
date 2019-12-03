package com.iths.robinhansson.cleancode.lab2.exceptions;

public class UnvalidScreenSizeException extends IllegalArgumentException {

    public UnvalidScreenSizeException(String message) {
        super(message);
    }
}
