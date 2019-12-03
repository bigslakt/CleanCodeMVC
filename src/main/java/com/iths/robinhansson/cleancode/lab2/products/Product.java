package com.iths.robinhansson.cleancode.lab2.products;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iths.robinhansson.cleancode.lab2.exceptions.ProductTypeDontExistException;
import org.springframework.data.annotation.Id;

import java.util.Objects;


public abstract class Product {

    @JsonIgnore
    private long productTypeId;

    @Id
    private long productId;
    private ProductType productType;
    private double price;
    private ProductColour colour;


    public Product() {
    }

    public Product(Product product) {

        this.productType = product.getProductType();
        this.price = product.getPrice();
        this.colour = product.getColour();
        this.productTypeId = product.getProductTypeId();
        this.productId = product.getProductId();
    }

    public Product(long productCategoryNumber, ProductType productType, double price, ProductColour colour) {
        this.productType = productType;
        this.price = price;
        this.colour = colour;
        this.productTypeId = (productCategoryNumber + setProductTypeId()) * 10000000;
    }

    public Product(long productCategoryNumber, ProductType productType, ProductColour colour) {
        this.productType = productType;
        this.colour = colour;
        this.productTypeId = (productCategoryNumber + setProductTypeId()) * 10000000;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public long getProductId() {
        return productId;
    }

    public ProductType getProductType() {
        return productType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ProductColour getColour() {
        return colour;
    }

    public void setColour(ProductColour colour) {
        this.colour = colour;
    }

    public long getProductTypeId() {
        return productTypeId;
    }

    private long setProductTypeId() {

        switch(this.getProductType()) {

            case COMPUTER:
            case COUCH:
                return 1;
            case TELEVISION:
            case TABLE:
                return 2;
            case CELLPHONE:
            case CHAIR:
                return 3;
            default:
                throw new ProductTypeDontExistException("There is no product of that type");
        }
    }

    public abstract Product cloneProduct();



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return  getProductId() == product.getProductId() &&
                Double.compare(product.getPrice(), getPrice()) == 0 &&
                getProductType() == product.getProductType() &&
                getColour() == product.getColour();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductTypeId(), getProductId(), getProductType(), getPrice(), getColour());
    }

    @Override
    public String toString() {
        return  " productId: " + productId +
                ", productType: '" + productType + '\'' +
                ", price: " + price + "SEK" +
                ", colour: '" + colour + '\'';
    }
}
