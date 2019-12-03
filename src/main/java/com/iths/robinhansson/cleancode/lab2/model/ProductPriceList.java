package com.iths.robinhansson.cleancode.lab2.model;

import com.iths.robinhansson.cleancode.lab2.exceptions.ProductTypeDontExistException;
import com.iths.robinhansson.cleancode.lab2.products.ProductType;

import java.util.HashMap;

public class ProductPriceList {

    private static HashMap<ProductType, Double> priceList= new HashMap<ProductType, Double>(){{
        put(ProductType.COMPUTER, 10500.67);
        put(ProductType.TELEVISION, 43789.99);
        put(ProductType.CELLPHONE, 6888.88);
        put(ProductType.COUCH, 35700.16);
        put(ProductType.TABLE, 28689.77);
        put(ProductType.CHAIR, 4500.99);
    }};

    public static HashMap<ProductType, Double> getProductPriceList() {
        return priceList;
    }

    public static double getProductPrice(ProductType productType) {

        if (priceList.containsKey(productType)) {
            return priceList.get(productType);
        }

        throw new ProductTypeDontExistException(" Product dont exist in the price list. ");
    }

    public static double setProductPrice(ProductType productType, Double newPrice) {

        if(newPrice == null) {
            throw new NullPointerException(" Value is null. ");
        }
        else if(newPrice < 1) {
            throw new IllegalArgumentException(" Value is zero or below. ");
        }

        if (priceList.containsKey(productType)) {
            priceList.replace(productType, newPrice);
            return priceList.get(productType);
        }

        throw new ProductTypeDontExistException(" Product dont exist in the price list. ");
    }
}
