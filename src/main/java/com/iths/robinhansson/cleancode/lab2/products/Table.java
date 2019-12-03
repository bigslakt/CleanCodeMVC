package com.iths.robinhansson.cleancode.lab2.products;

public class Table extends Furniture {

    public Table(ProductType productType, double price, ProductColour colour, FurnitureMaterial furnitureMaterial) {
        super(productType, price, colour, furnitureMaterial);
    }

    public Table(ProductType productType, ProductColour colour, FurnitureMaterial furnitureMaterial) {
        super(productType, colour, furnitureMaterial);
    }

    public Table() {
    }

    public Table(Product product) {
        super(product);
    }

    @Override
    public Product cloneProduct() {
        return new Table(this);
    }
}
