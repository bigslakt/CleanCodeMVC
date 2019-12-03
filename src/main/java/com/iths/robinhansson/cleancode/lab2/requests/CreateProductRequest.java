package com.iths.robinhansson.cleancode.lab2.requests;

import java.util.Optional;

public class CreateProductRequest {

    private String productType;
    private String productColour;
    private Double screenSize;
    private String furnitureMaterial;

    public CreateProductRequest() {
    }

    public CreateProductRequest(String productType, String productColour, Double screenSize) {
        this.productType = productType;
        this.productColour = productColour;
        this.screenSize = screenSize;
    }

    public CreateProductRequest(String productType, String productColour, String furnitureMaterial) {
        this.productType = productType;
        this.productColour = productColour;
        this.furnitureMaterial = furnitureMaterial;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductColour() {
        return productColour;
    }

    public void setProductColour(String productColour) {
        this.productColour = productColour;
    }

    public Optional<Double> getScreenSize() {
        return Optional.ofNullable(screenSize);
    }

    public void setScreenSize(Double screenSize) {
        this.screenSize = screenSize;
    }

    public Optional<String> getFurnitureMaterial() {
        return Optional.ofNullable(furnitureMaterial);
    }

    public void setFurnitureMaterial(String furnitureMaterial) {
        this.furnitureMaterial = furnitureMaterial;
    }
}
