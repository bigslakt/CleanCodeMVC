package com.iths.robinhansson.cleancode.lab2.model;

import com.iths.robinhansson.cleancode.lab2.products.Product;

import java.util.HashMap;


public interface IProductStorage {

    HashMap<Long, Product> getAllProducts();
    Product getProduct(long id);
    Product addProduct(Product product);
    Product updateProduct(Product updatedProduct);
    boolean deleteProduct(long id);

}
