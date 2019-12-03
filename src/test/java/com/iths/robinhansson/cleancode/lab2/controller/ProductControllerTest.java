package com.iths.robinhansson.cleancode.lab2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iths.robinhansson.cleancode.lab2.model.IProductStorage;
import com.iths.robinhansson.cleancode.lab2.model.ProductPriceList;
import com.iths.robinhansson.cleancode.lab2.products.*;
import org.junit.Before;
import org.junit.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    IProductStorage productStorage;

    private HashMap<Long, Product> products;

    private Product television;
    private Product couch;


    @Before
    public void setUpInventory() throws Exception {

        products = new HashMap<>();

        television = new Television(ProductType.TELEVISION, ProductColour.RED, 55.5);
        couch = new Couch(ProductType.COUCH, ProductColour.BLACK, FurnitureMaterial.WOOD);

        products.put(television.getProductTypeId(), television);
        products.put(couch.getProductTypeId(), couch);
    }


    @Test
    public void testCreateTelevision() throws Exception {

        String jsonString = String.format("{\"productType\":\"%s\",\"productColour\":\"%s\",\"screenSize\":%s}",
                String.valueOf(television.getProductType()), String.valueOf(television.getColour()), String.valueOf(((Electronic)television).getScreenSize()));

        when(productStorage.addProduct(television)).thenReturn(television);

        MvcResult result = mvc.perform(post("/products/")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(jsonString))
                .andExpect(status().isCreated())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        Product responseProduct = mapper.readValue(json, Television.class);

        assertEquals(television, responseProduct);
    }

    @Test
    public void testCreateCouch() throws Exception {

        String jsonString = String.format("{\"productType\":\"%s\",\"productColour\":\"%s\",\"furnitureMaterial\":\"%s\"}",
                String.valueOf(couch.getProductType()), String.valueOf(couch.getColour()), String.valueOf(((Furniture)couch).getFurnitureMaterial()));

        when(productStorage.addProduct(couch)).thenReturn(couch);

        MvcResult result = mvc.perform(post("/products/")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(jsonString))
                .andExpect(status().isCreated())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        Product responseProduct = mapper.readValue(json, Couch.class);

        assertEquals(couch, responseProduct);
    }

    @Test
    public void testUpdateProduct () throws Exception {

        Product updatedCouch = new Couch(ProductType.COUCH, ProductColour.GREEN, FurnitureMaterial.WOOD);

        ProductColour newColour = ProductColour.GREEN;

        String jsonString = String.format("{\"productColour\":\"%s\"}", String.valueOf(newColour));

        when(productStorage.getProduct(couch.getProductTypeId())).thenReturn(couch);
        when(productStorage.updateProduct(updatedCouch)).thenReturn(updatedCouch);

        MvcResult result = mvc.perform(put(String.format("/products/%d", couch.getProductTypeId()))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(jsonString))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        Product responseProduct = mapper.readValue(json, Couch.class);

        assertEquals(newColour, responseProduct.getColour());

    }

    @Test
    public void testDeleteProduct () throws Exception {

        when(productStorage.deleteProduct(couch.getProductTypeId())).thenReturn(true);

        mvc.perform(delete(String.format("/products/%s", couch.getProductTypeId()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }



    //Affärslogik-----------------------------------------

    @Test
    public void testGetProductPrice() throws Exception {

        MvcResult result = mvc.perform(get("/products/pricelist/cellphone")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonBody = result.getResponse().getContentAsString();
        String returnedPrice = mapper.readValue(jsonBody, String.class);

        assertEquals(String.valueOf(ProductPriceList.getProductPrice(ProductType.CELLPHONE)), returnedPrice);

    }

    @Test
    public void testUpdateProductPrice() throws Exception {

        double expected = 500.5;

        String jsonString = String.format("{\"price\":%s}", expected);

        when(productStorage.getAllProducts()).thenReturn(products);

        MvcResult result = mvc.perform(get("/products/pricelist/television")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(jsonString))
                .andExpect(status().isOk())
                .andReturn();

        String jsonBody = result.getResponse().getContentAsString();
        String returnedPrice = mapper.readValue(jsonBody, String.class);

        assertEquals(String.valueOf(ProductPriceList.getProductPrice(ProductType.TELEVISION)), returnedPrice);
    }

    @Test
    public void testGetAllOfOneProductCategory() throws Exception {

        when(productStorage.getAllProducts()).thenReturn(products);

        MvcResult result = mvc.perform(get("/products/allOfCategory/electronic")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonBody = result.getResponse().getContentAsString();
        List<Product> responseProducts = Arrays.asList(mapper.readValue(jsonBody, Television[].class));

        Product product1 = responseProducts.get(0);

        assertEquals(television, product1);
    }

    @Test
    public void testGetAllOfOneProductType() throws Exception {

        when(productStorage.getAllProducts()).thenReturn(products);

        MvcResult result = mvc.perform(get("/products/allOfType/couch")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonBody = result.getResponse().getContentAsString();
        List<Product> responseProducts = Arrays.asList(mapper.readValue(jsonBody, Couch[].class));

        Product product1 = responseProducts.get(0);

        assertEquals(couch, product1);
    }

    @Test
    public void testGetTotalPriceOfProducts() throws Exception {

        television.setPrice(ProductPriceList.getProductPrice(ProductType.TELEVISION));
        couch.setPrice(ProductPriceList.getProductPrice(ProductType.COUCH));

        double expected = television.getPrice() + couch.getPrice();

        when(productStorage.getAllProducts()).thenReturn(products);

        MvcResult result = mvc.perform(get("/products/totalPrice")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonBody = result.getResponse().getContentAsString();
        double responsePrice = mapper.readValue(jsonBody, Double.class);

        assertEquals(expected, responsePrice);
    }

    @Test
    public void testGetTotalPriceOfProductCategory() throws Exception {

        television.setPrice(ProductPriceList.getProductPrice(ProductType.TELEVISION));
        couch.setPrice(ProductPriceList.getProductPrice(ProductType.COUCH));

        double expected = television.getPrice();

        when(productStorage.getAllProducts()).thenReturn(products);

        MvcResult result = mvc.perform(get("/products/totalPrice/category/electronic")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonBody = result.getResponse().getContentAsString();
        double responsePrice = mapper.readValue(jsonBody, Double.class);

        assertEquals(expected, responsePrice);
    }

    @Test
    public void testGetTotalPriceOfProductType() throws Exception {

        television.setPrice(ProductPriceList.getProductPrice(ProductType.TELEVISION));
        couch.setPrice(ProductPriceList.getProductPrice(ProductType.COUCH));

        double expected = couch.getPrice();

        when(productStorage.getAllProducts()).thenReturn(products);

        MvcResult result = mvc.perform(get("/products/totalPrice/type/couch")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonBody = result.getResponse().getContentAsString();
        double responsePrice = mapper.readValue(jsonBody, Double.class);

        assertEquals(expected, responsePrice);
    }
}