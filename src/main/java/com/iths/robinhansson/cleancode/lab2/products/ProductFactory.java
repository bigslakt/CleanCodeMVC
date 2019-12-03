package com.iths.robinhansson.cleancode.lab2.products;

import com.iths.robinhansson.cleancode.lab2.exceptions.ProductTypeDontExistException;

public class ProductFactory {

    public Product createProduct(ProductType productType, ProductColour colour, double screenSize) {

        switch (productType) {

            case COMPUTER:
                return new Computer(productType, colour, screenSize);
            case TELEVISION:
                return new Television(productType, colour, screenSize);
            case CELLPHONE:
                return new Cellphone(productType, colour, screenSize);
            default:
                throw new ProductTypeDontExistException("The electronic-type does not exist");
        }
    }

    public Product createProduct(ProductType productType, ProductColour colour, FurnitureMaterial furnitureMaterial) {

        switch (productType) {

            case COUCH:
                return new Couch(productType, colour, furnitureMaterial);
            case TABLE:
                return new Table(productType, colour, furnitureMaterial);
            case CHAIR:
                return new Chair(productType, colour, furnitureMaterial);
            default:
                throw new ProductTypeDontExistException("The furniture-type does not exist");
        }
    }
}
