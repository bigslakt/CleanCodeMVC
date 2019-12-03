package com.iths.robinhansson.cleancode.lab2.model;

import com.iths.robinhansson.cleancode.lab2.products.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

public class ProductService {

    IProductStorage productStorage;

    public ProductService(IProductStorage productStorage) {
        this.productStorage = productStorage;
    }

    public HashMap<ProductType, Double> getPriceList() {

        setPriceForAllProducts();
        return ProductPriceList.getProductPriceList();
    }

    public double getPriceForProductType(ProductType productType) {

       return ProductPriceList.getProductPrice(productType);
    }

    public double setPriceForProductType(ProductType productType, double newPrice) {

        double price = ProductPriceList.setProductPrice(productType, newPrice);

        HashMap<Long, Product> products = productStorage.getAllProducts();

        if(!products.isEmpty()) {

            for (Product product : products.values()) {

                if (product.getProductType() == productType) {

                    product.setPrice(newPrice);
                    productStorage.updateProduct(product);
                }
            }
        }
        return price;
    }

    private void setPriceForAllProducts() {

        HashMap<Long, Product> products = productStorage.getAllProducts();

        if(!products.isEmpty()) {

            for (Product product : products.values()) {

                product.setPrice(ProductPriceList.getProductPrice(product.getProductType()));
                productStorage.updateProduct(product);
            }
        }
    }

    public ArrayList<Product> getAllOfOneProductCategory(ProductCategory productCategory) {

        ArrayList<Product> productsOfCategory = new ArrayList<>();

        HashMap<Long, Product> products = productStorage.getAllProducts();

        for (Product product : products.values()) {

            if(productCategory == ProductCategory.ELECTRONIC) {

                if(product instanceof Electronic) {
                    productsOfCategory.add(product);
                }
            }
            else if(productCategory == ProductCategory.FURNITURE) {

                if(product instanceof Furniture) {
                    productsOfCategory.add(product);
                }
            }
        }
        return productsOfCategory;
    }

    public ArrayList<Product> getAllOfOneProductType(ProductType productType) {

        ArrayList<Product> productsOfType = new ArrayList<>();

        HashMap<Long, Product> products = productStorage.getAllProducts();

        for (Product product : products.values()) {

            if(product.getProductType() == productType) {

                productsOfType.add(product);
            }
        }

        return productsOfType;
    }


    public BigDecimal getTotalPriceOfProducts() {

        double totalPrice = 0;

        HashMap<Long, Product> products = productStorage.getAllProducts();

        for (Product product : products.values()) {

            totalPrice += product.getPrice();
        }

        BigDecimal formattedPrice = new BigDecimal(totalPrice);

        return formattedPrice;
    }

    public BigDecimal getTotalPriceOfProductCategory(ProductCategory productCategory) {

        double totalPrice = 0;

        HashMap<Long, Product> products = productStorage.getAllProducts();

        for (Product product : products.values()) {

            if(productCategory == ProductCategory.ELECTRONIC) {

                if(product instanceof Electronic) {
                    totalPrice += product.getPrice();
                }
            }
            else if(productCategory == ProductCategory.FURNITURE) {

                if(product instanceof Furniture) {
                    totalPrice += product.getPrice();
                }
            }
        }
        BigDecimal formattedPrice = new BigDecimal(totalPrice);
        return formattedPrice;
    }

    public BigDecimal getTotalPriceOfProductType(ProductType productType) {

        double totalPrice = 0;

        HashMap<Long, Product> products = productStorage.getAllProducts();

        for (Product product : products.values()) {

            if(product.getProductType() == productType) {

                totalPrice += product.getPrice();
            }
        }
        BigDecimal formattedPrice = new BigDecimal(totalPrice);
        return formattedPrice;
    }

    public ProductCategory[] getProductCategories() {

        return ProductCategory.values();
    }

    public ProductType[] getProductTypes() {

        return ProductType.values();
    }

    public ProductColour[] getProductColours() {

        return ProductColour.values();
    }

    public FurnitureMaterial[] getFurnitureMaterials() {

        return FurnitureMaterial.values();
    }
}