package com.iths.robinhansson.cleancode.lab2.products;

public class Couch extends Furniture {

    public Couch(ProductType productType, double price, ProductColour colour, FurnitureMaterial furnitureMaterial) {
        super(productType, price, colour, furnitureMaterial);
    }

    public Couch(ProductType productType, ProductColour colour, FurnitureMaterial furnitureMaterial) {
        super(productType, colour, furnitureMaterial);
    }

    public Couch() {
    }

    public Couch(Product product) {
        super(product);
    }

    @Override
    public Product cloneProduct() {
        return new Couch(this);
    }
}
