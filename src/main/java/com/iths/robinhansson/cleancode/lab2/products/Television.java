package com.iths.robinhansson.cleancode.lab2.products;

public class Television extends Electronic {

    public Television(ProductType productType, double price, ProductColour colour, double screenSize) {
        super(productType, price, colour, screenSize);
    }

    public Television(ProductType productType, ProductColour colour, double screenSize) {
        super(productType, colour, screenSize);
    }

    public Television() {
    }

    public Television(Product product) {
        super(product);
    }

    @Override
    public Product cloneProduct() {
        return new Television(this);
    }

}


