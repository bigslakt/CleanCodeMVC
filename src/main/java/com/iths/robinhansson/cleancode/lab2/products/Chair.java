package com.iths.robinhansson.cleancode.lab2.products;

public class Chair extends Furniture {

    public Chair(ProductType productType, double price, ProductColour colour, FurnitureMaterial furnitureMaterial) {
        super(productType, price, colour, furnitureMaterial);
    }

    public Chair(ProductType productType, ProductColour colour, FurnitureMaterial furnitureMaterial) {
        super(productType, colour, furnitureMaterial);
    }

    public Chair() {
    }

    public Chair(Product product) {
        super(product);
    }

    @Override
    public Product cloneProduct() {
        return new Chair(this);
    }
}
