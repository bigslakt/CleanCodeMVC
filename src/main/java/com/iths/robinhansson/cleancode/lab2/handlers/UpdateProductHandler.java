package com.iths.robinhansson.cleancode.lab2.handlers;

import com.iths.robinhansson.cleancode.lab2.exceptions.ParametersConflictException;
import com.iths.robinhansson.cleancode.lab2.model.IProductStorage;
import com.iths.robinhansson.cleancode.lab2.products.*;
import com.iths.robinhansson.cleancode.lab2.requests.UpdateProductRequest;
import com.iths.robinhansson.cleancode.lab2.tools.RequestParametersConverter;

import java.util.Optional;

public class UpdateProductHandler {

    private IProductStorage productStorage;
    private RequestParametersConverter converter;

    public UpdateProductHandler(IProductStorage productStorage, RequestParametersConverter converter) {
        this.productStorage = productStorage;
        this.converter = converter;
    }

    public Product updateProduct (Product product, UpdateProductRequest updateProductRequest) {

        Product updatedProduct = product.cloneProduct();

        Optional.ofNullable(updateProductRequest).ifPresent(optUpdateRequest -> {

            optUpdateRequest.getProductColour().ifPresent(n -> {
                updatedProduct.setColour(converter.convertProductColour(String.valueOf(n)));
            });

            optUpdateRequest.getScreenSize().ifPresent(n -> {
                if(updatedProduct instanceof Electronic) {
                    ((Electronic) updatedProduct).setScreenSize(converter.controlScreenSize(Double.parseDouble(String.valueOf(n))));
                }
                else {
                    throw new ParametersConflictException(" Current product does'nt have a screen. ");
                }
            });

            optUpdateRequest.getFurnitureMaterial().ifPresent(n -> {
                if(updatedProduct instanceof Furniture) {
                    ((Furniture) updatedProduct).setFurnitureMaterial(converter.convertFurnitureMaterial(String.valueOf(n)));
                }
                else {
                    throw new ParametersConflictException(" Current products material can not be modified. ");
                }
            });
        });

        return productStorage.updateProduct(updatedProduct);
    }
}
