package com.iths.robinhansson.cleancode.lab2.handlers;

import com.iths.robinhansson.cleancode.lab2.exceptions.*;
import com.iths.robinhansson.cleancode.lab2.model.IProductStorage;
import com.iths.robinhansson.cleancode.lab2.products.*;
import com.iths.robinhansson.cleancode.lab2.requests.CreateProductRequest;
import com.iths.robinhansson.cleancode.lab2.tools.RequestParametersConverter;

import java.util.Optional;

public class CreateProductHandler {

    IProductStorage productStorage;
    private ProductFactory productFactory;
    private RequestParametersConverter converter;

    private ProductType productType;
    private ProductColour productColour;
    private Double screenSize;
    private FurnitureMaterial furnitureMaterial;

    public CreateProductHandler(IProductStorage productStorage, ProductFactory productFactory, RequestParametersConverter converter) {

        this.productStorage = productStorage;
        this.productFactory = productFactory;
        this.converter = converter;
    }

    public Product createProduct (CreateProductRequest createProductRequest) {

        Product product;

        convertRequestValues(createProductRequest);

        if(screenSize == null && furnitureMaterial == null) {

            throw new ParametersMissingException(" You have to use either screen-size or furniture-material to create a product. ");
        }
        else if(screenSize != null && furnitureMaterial != null) {

            throw new ParametersConflictException(" You cant create a product with both screen-size and furniture-material. ");
        }

        if(screenSize != null) {
            product = productFactory.createProduct(this.productType, this.productColour, this.screenSize);
        }
        else {
            product = productFactory.createProduct(this.productType, this.productColour, this.furnitureMaterial);
        }

        product = productStorage.addProduct(product);
        return  product;
    }

    private void convertRequestValues(CreateProductRequest createProductRequest) {

        this.productType = converter.convertProductType(createProductRequest.getProductType());
        this.productColour = converter.convertProductColour(createProductRequest.getProductColour());

        Optional.ofNullable(createProductRequest).ifPresent(optCreateRequest -> {

            optCreateRequest.getScreenSize().ifPresent(n -> {
                this.screenSize = converter.controlScreenSize(Double.parseDouble(String.valueOf(n)));
            });

            optCreateRequest.getFurnitureMaterial().ifPresent(n -> {
                this.furnitureMaterial = converter.convertFurnitureMaterial(String.valueOf(n));
            });
        });
    }
}
