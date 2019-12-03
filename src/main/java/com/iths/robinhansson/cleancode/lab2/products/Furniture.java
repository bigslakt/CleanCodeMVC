package com.iths.robinhansson.cleancode.lab2.products;

import java.util.Objects;

public abstract class Furniture extends Product {

    private static final long PRODUCT_CATEGORY_NUMBER = 200;
    private FurnitureMaterial furnitureMaterial;

    public Furniture(ProductType productType, double price, ProductColour colour, FurnitureMaterial furnitureMaterial) {
        super(PRODUCT_CATEGORY_NUMBER, productType, price, colour);
        this.furnitureMaterial = furnitureMaterial;
    }

    public Furniture(ProductType productType, ProductColour colour, FurnitureMaterial furnitureMaterial) {
        super(PRODUCT_CATEGORY_NUMBER, productType, colour);
        this.furnitureMaterial = furnitureMaterial;
    }

    public Furniture() {
    }

    public Furniture(Product product) {
        super(product);
        this.furnitureMaterial = ((Furniture)product).getFurnitureMaterial();
    }

    public FurnitureMaterial getFurnitureMaterial() {
        return furnitureMaterial;
    }

    public void setFurnitureMaterial(FurnitureMaterial furnitureMaterial) {
        this.furnitureMaterial = furnitureMaterial;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Furniture)) return false;
        if (!super.equals(o)) return false;
        Furniture furniture = (Furniture) o;
        return getFurnitureMaterial() == furniture.getFurnitureMaterial();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getFurnitureMaterial());
    }

    @Override
    public String toString() {
        return "{" +
                super.toString() +
                ", furnitureMaterial: '" + furnitureMaterial + '\'' +
                '}';
    }
}
