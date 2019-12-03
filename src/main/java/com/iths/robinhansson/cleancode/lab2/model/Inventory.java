package com.iths.robinhansson.cleancode.lab2.model;

import com.iths.robinhansson.cleancode.lab2.products.Product;
import com.mongodb.client.result.DeleteResult;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.HashMap;

public class Inventory implements IProductStorage {

    private final MongoTemplate products;

    public Inventory(MongoTemplate mongotemplate) {
        this.products = mongotemplate;
    }

    @Override
    public HashMap<Long, Product> getAllProducts() {

        HashMap<Long, Product> map = new HashMap<>();
        List<Product> productList = products.findAll(Product.class);

        for (Product product : productList) {

            map.put(product.getProductId(), product);
        }

        return map;
    }

    @Override
    public Product getProduct(long productId) {

        Query query = new Query();
        query.addCriteria(Criteria.where("productId").is(productId));
        return products.findOne(query, Product.class);
    }

    @Override
    public Product addProduct(Product product) {

        long productId = generateUniqueId(product);
        product.setProductId(productId);

        return products.save(product);
    }

    @Override
    public Product updateProduct(Product updatedProduct) {

        return products.save(updatedProduct);
    }

    @Override
    public boolean deleteProduct(long productId) {

        Query query = new Query();
        query.addCriteria(Criteria.where("productId").is(productId));
        DeleteResult result = products.remove(query, Product.class);

        return result.getDeletedCount() > 0;
    }

    private long generateUniqueId(Product product) {

        long highestId = 0;

        HashMap<Long, Product> productList = getAllProducts();

        for (Product listProduct : productList.values()) {

            if(listProduct.getProductType() == product.getProductType()) {

                if(listProduct.getProductTypeId() > highestId) {

                    highestId = listProduct.getProductId();
                }
            }
        }

        if(highestId == 0) {

            highestId = product.getProductTypeId();
        }

        highestId++;

        return highestId;
    }
}
