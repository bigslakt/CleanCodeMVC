package com.iths.robinhansson.cleancode.lab2.requests;

import java.util.Optional;

public class UpdateProductRequest {

    private String productColour;
    private Double screenSize;
    private String furnitureMaterial;

    public UpdateProductRequest() {
    }

    public Optional<String> getProductColour() {
        return Optional.ofNullable(productColour);
    }

    public void setProductColour(String productColour) {
        this.productColour = productColour;
    }

    public Optional<Double> getScreenSize() {
        return Optional.ofNullable(screenSize);
    }

    public void setScreenSize(double screenSize) {
        this.screenSize = screenSize;
    }

    public Optional<String> getFurnitureMaterial() {
        return Optional.ofNullable(furnitureMaterial);
    }

    public void setFurnitureMaterial(String furnitureMaterial) {
        this.furnitureMaterial = furnitureMaterial;
    }
}
