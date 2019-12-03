package com.iths.robinhansson.cleancode.lab2.products;

public class Computer extends Electronic {

    public Computer(ProductType productType, double price, ProductColour colour, double screenSize) {
        super(productType, price, colour, screenSize);
    }

    public Computer(ProductType productType, ProductColour colour, double screenSize) {
        super(productType, colour, screenSize);
    }

    public Computer() {
    }

    public Computer(Product product) {
        super(product);
    }

    @Override
    public Product cloneProduct() {
        return new Computer(this);
    }
}
