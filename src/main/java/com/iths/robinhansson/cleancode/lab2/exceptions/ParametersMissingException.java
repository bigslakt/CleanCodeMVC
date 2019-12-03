package com.iths.robinhansson.cleancode.lab2.exceptions;

public class ParametersMissingException extends IllegalArgumentException {

    public ParametersMissingException(String message) {
        super(message);
    }
}
