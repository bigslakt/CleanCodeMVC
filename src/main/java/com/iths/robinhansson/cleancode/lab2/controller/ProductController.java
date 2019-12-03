package com.iths.robinhansson.cleancode.lab2.controller;


import com.iths.robinhansson.cleancode.lab2.exceptions.ProductDontExistException;
import com.iths.robinhansson.cleancode.lab2.exceptions.ProductTypeDontExistException;
import com.iths.robinhansson.cleancode.lab2.handlers.CreateProductHandler;
import com.iths.robinhansson.cleancode.lab2.handlers.UpdateProductHandler;
import com.iths.robinhansson.cleancode.lab2.model.ProductService;
import com.iths.robinhansson.cleancode.lab2.products.*;
import com.iths.robinhansson.cleancode.lab2.requests.CreateProductRequest;
import com.iths.robinhansson.cleancode.lab2.model.IProductStorage;
import com.iths.robinhansson.cleancode.lab2.requests.UpdateProductRequest;
import com.iths.robinhansson.cleancode.lab2.tools.RequestParametersConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/products")
public class ProductController {

    private IProductStorage productStorage;
    private ProductFactory productFactory;
    private RequestParametersConverter converter;
    private ProductService productService;


    public ProductController(IProductStorage productStorage) {
        this.productStorage = productStorage;
        this.productFactory = new ProductFactory();
        this.converter  = new RequestParametersConverter();
        this.productService = new ProductService(this.productStorage);

    }

    @GetMapping("/")
    public ResponseEntity<HashMap<String, String[]>> getAllProducts() {

        try {
            return new ResponseEntity(productStorage.getAllProducts(), HttpStatus.OK);
        }catch(Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/{productId}")
    public ResponseEntity getProduct(@PathVariable(name="productId") long productId) {

        try {
            Product product = productStorage.getProduct(productId);
            return new ResponseEntity(product, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    public ResponseEntity createProduct(@Valid @RequestBody (required=true) CreateProductRequest createProductRequest) {

        CreateProductHandler createProductHandler = new CreateProductHandler(productStorage, productFactory, converter);

        try {
            Product product = createProductHandler.createProduct(createProductRequest);
            return new ResponseEntity(product, HttpStatus.CREATED);
        }catch(IllegalArgumentException argEx){
            return new ResponseEntity(argEx.getMessage(), HttpStatus.BAD_REQUEST);
        }catch(Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @PutMapping("/{productId}")
    public ResponseEntity updateProduct(@RequestBody UpdateProductRequest updateProductRequest, @PathVariable(name="productId") String productId) {

        UpdateProductHandler updateProductHandler = new UpdateProductHandler(productStorage, converter);

        try {
            long id = Long.parseLong(productId);
            Product product = productStorage.getProduct(id);
            Product updatedProduct = updateProductHandler.updateProduct(product, updateProductRequest);
            return new ResponseEntity(updatedProduct, HttpStatus.OK);
        }catch(ProductTypeDontExistException notFoundEx) {
            return new ResponseEntity(notFoundEx.getMessage(), HttpStatus.NOT_FOUND);
        }catch(IllegalArgumentException argEx){
            return new ResponseEntity(argEx.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity deleteProduct(@PathVariable(name="productId") String productId) {

        String message = " Product: " + productId + " not deleted. ";

        try {

            long id = Long.parseLong(productId);

            if (productStorage.deleteProduct(id)) {

                message = " Product: " + productId + " successfully deleted. ";
                return new ResponseEntity(message, HttpStatus.OK);
            }
            else {
                return new ResponseEntity(message, HttpStatus.BAD_GATEWAY);
            }

        }catch(NumberFormatException formatEx){
            return new ResponseEntity(formatEx.getMessage(), HttpStatus.BAD_REQUEST);
        }catch(ProductDontExistException notFoundEx) {
            return new ResponseEntity(notFoundEx.getMessage(), HttpStatus.NOT_FOUND);
        }catch(Exception e) {
            return new ResponseEntity(e.getMessage() + message, HttpStatus.BAD_GATEWAY);
        }
    }



    //Affärslogik-----------------------------------------

    @GetMapping("/pricelist")
    public ResponseEntity getPriceList() {

        try {
            return new ResponseEntity(productService.getPriceList(), HttpStatus.OK);
        }catch(Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/pricelist/{productType}")
    public ResponseEntity getProductPrice(@PathVariable(name="productType") String productType) {

        double productPrice;

        try {
            ProductType convertedProductType = converter.convertProductType(productType);
            productPrice = productService.getPriceForProductType(convertedProductType);
            return new ResponseEntity(productPrice, HttpStatus.OK);
        }catch(IllegalArgumentException argEx) {
            return new ResponseEntity(argEx.getMessage(), HttpStatus.BAD_REQUEST);
        }catch(Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @PutMapping("/pricelist/{productType}")
    public ResponseEntity updateProductPrice(@RequestBody double newPrice, @PathVariable(name="productType") String productType) {

        try {
            ProductType convertedProductType = converter.convertProductType(productType);
            productService.setPriceForProductType(convertedProductType, newPrice);
            double price = productService.getPriceForProductType(convertedProductType);
            return new ResponseEntity(price, HttpStatus.OK);
        }catch(NullPointerException | IllegalArgumentException requestEx){
            return new ResponseEntity(requestEx.getMessage(), HttpStatus.BAD_REQUEST);
        } catch(Exception e) {
            return new ResponseEntity(e, HttpStatus.BAD_GATEWAY);
        }
    }



    //Affärslogik-----------------------------------------


    @GetMapping("/allOfCategory/{productCategory}")
    public ResponseEntity getAllOfOneProductCategory(@PathVariable(name="productCategory") String productCategory) {

        try {
            ProductCategory convertedProductCategory = converter.convertProductCategory(productCategory);
            ArrayList<Product> productsOfCategory = productService.getAllOfOneProductCategory(convertedProductCategory);
            return new ResponseEntity(productsOfCategory, HttpStatus.OK);
        }catch(IllegalArgumentException argEx) {
            return new ResponseEntity(argEx.getMessage(), HttpStatus.BAD_REQUEST);
        }catch(Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/allOfType/{productType}")
    public ResponseEntity getAllOfOneProductType(@PathVariable(name="productType") String productType) {

        try {
            ProductType convertedProductType = converter.convertProductType(productType);
            ArrayList<Product> productsOfType = productService.getAllOfOneProductType(convertedProductType);
            return new ResponseEntity(productsOfType, HttpStatus.OK);
        }catch(IllegalArgumentException argEx) {
            return new ResponseEntity(argEx.getMessage(), HttpStatus.BAD_REQUEST);
        }catch(Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }




    //Affärslogik-----------------------------------------


    @GetMapping("/totalPrice")
    public ResponseEntity getTotalPriceOfProducts() {

        BigDecimal totalPrice;

        try {
            totalPrice = productService.getTotalPriceOfProducts();
            return new ResponseEntity(totalPrice, HttpStatus.OK);
        }catch(Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/totalPrice/category/{productCategory}")
    public ResponseEntity getTotalPriceOfProductCategory(@PathVariable(name="productCategory") String productCategory) {

        try {
            ProductCategory convertedProductCategory = converter.convertProductCategory(productCategory);
            BigDecimal totalPriceOfCategory = productService.getTotalPriceOfProductCategory(convertedProductCategory);
            return new ResponseEntity(totalPriceOfCategory, HttpStatus.OK);
        }catch(IllegalArgumentException argEx) {
            return new ResponseEntity(argEx.getMessage(), HttpStatus.BAD_REQUEST);
        }catch(Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/totalPrice/type/{productType}")
    public ResponseEntity getTotalPriceOfProductType(@PathVariable(name="productType") String productType) {

        try {
            ProductType convertedProductType = converter.convertProductType(productType);
            BigDecimal totalPriceOfType = productService.getTotalPriceOfProductType(convertedProductType);
            return new ResponseEntity(totalPriceOfType, HttpStatus.OK);
        }catch(IllegalArgumentException argEx) {
            return new ResponseEntity(argEx.getMessage(), HttpStatus.BAD_REQUEST);
        }catch(Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
}
