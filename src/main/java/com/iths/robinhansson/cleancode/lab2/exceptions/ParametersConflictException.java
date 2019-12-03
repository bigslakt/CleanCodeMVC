package com.iths.robinhansson.cleancode.lab2.exceptions;

public class ParametersConflictException extends IllegalArgumentException {

    public ParametersConflictException(String message) {
        super(message);
    }
}
