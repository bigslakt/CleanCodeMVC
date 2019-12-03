package com.iths.robinhansson.cleancode.lab2.tools;

import com.iths.robinhansson.cleancode.lab2.exceptions.FurnitureMaterialDontExistException;
import com.iths.robinhansson.cleancode.lab2.exceptions.ProductColourDontExistException;
import com.iths.robinhansson.cleancode.lab2.exceptions.ProductTypeDontExistException;
import com.iths.robinhansson.cleancode.lab2.exceptions.UnvalidScreenSizeException;
import com.iths.robinhansson.cleancode.lab2.products.FurnitureMaterial;
import com.iths.robinhansson.cleancode.lab2.products.ProductCategory;
import com.iths.robinhansson.cleancode.lab2.products.ProductColour;
import com.iths.robinhansson.cleancode.lab2.products.ProductType;
import org.apache.commons.lang3.EnumUtils;

public class RequestParametersConverter {

    public ProductCategory convertProductCategory(String category) {

        ProductCategory productCategory;

        if(EnumUtils.isValidEnum(ProductCategory.class, category.toUpperCase())) {

            productCategory = ProductCategory.valueOf(category.toUpperCase());
            return productCategory;
        }

        throw new ProductTypeDontExistException(" The product-category dont exist. ");
    }

    public ProductType convertProductType (String type) {

        ProductType productType;

        if(EnumUtils.isValidEnum(ProductType.class, type.toUpperCase())) {

            productType = ProductType.valueOf(type.toUpperCase());
            return productType;
        }

        throw new ProductTypeDontExistException(" The product-type dont exist. ");
    }

    public ProductColour convertProductColour (String colour) {

        ProductColour productColour;

        if(EnumUtils.isValidEnum(ProductColour.class, colour.toUpperCase())) {

            productColour = ProductColour.valueOf(colour.toUpperCase());
            return productColour;
        }

        throw new ProductColourDontExistException(" The product-colour dont exist. ");
    }

    public double controlScreenSize(double size) {

        if(size > 5) {

            return size;
        }

        throw new UnvalidScreenSizeException(" Un-valid screen-size. ");
    }

    public FurnitureMaterial convertFurnitureMaterial (String material) {

        FurnitureMaterial furnitureMaterial;

        if(EnumUtils.isValidEnum(FurnitureMaterial.class, material.toUpperCase())) {

            furnitureMaterial = FurnitureMaterial.valueOf(material.toUpperCase());
            return furnitureMaterial;
        }

        throw new FurnitureMaterialDontExistException(" The furniture-material dont exist. ");
    }
}
