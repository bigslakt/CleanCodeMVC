package com.iths.robinhansson.cleancode.lab2.exceptions;

public class FurnitureMaterialDontExistException extends IllegalArgumentException {

    public FurnitureMaterialDontExistException(String message) {
        super(message);
    }
}
