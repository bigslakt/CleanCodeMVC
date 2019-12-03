package com.iths.robinhansson.cleancode.lab2.products;

public class Cellphone extends Electronic {

    public Cellphone(ProductType productType, double price, ProductColour colour, double screenSize) {
        super(productType, price, colour, screenSize);
    }

    public Cellphone(ProductType productType, ProductColour colour, double screenSize) {
        super(productType, colour, screenSize);
    }

    public Cellphone() {
    }

    public Cellphone(Product product) {
        super(product);
    }

    @Override
    public Product cloneProduct() {
        return new Cellphone(this);
    }
}
