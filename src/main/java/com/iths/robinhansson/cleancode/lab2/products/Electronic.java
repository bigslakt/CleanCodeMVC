package com.iths.robinhansson.cleancode.lab2.products;

import com.iths.robinhansson.cleancode.lab2.exceptions.ProductTypeDontExistException;

import java.util.Objects;

public abstract class Electronic extends Product {

    private static final long PRODUCT_CATEGORY_NUMBER = 100;
    private double screenSize;

    public Electronic(ProductType productType, double price, ProductColour colour, double screenSize) {
        super(PRODUCT_CATEGORY_NUMBER, productType, price, colour);
        this.screenSize = screenSize;
    }

    public Electronic(ProductType productType, ProductColour colour, double screenSize) {
        super(PRODUCT_CATEGORY_NUMBER, productType, colour);
        this.screenSize = screenSize;
    }

    public Electronic() {
    }

    public Electronic(Product product) {
        super(product);
        this.screenSize = ((Electronic)product).getScreenSize();
    }

    public double getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(double screenSize) {
        this.screenSize = screenSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Electronic)) return false;
        if (!super.equals(o)) return false;
        Electronic that = (Electronic) o;
        return Double.compare(that.getScreenSize(), getScreenSize()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getScreenSize());
    }

    @Override
    public String toString() {
        return "{" +
                super.toString() +
                ", screenSize: " + screenSize + "\"" +
                '}';
    }
}
