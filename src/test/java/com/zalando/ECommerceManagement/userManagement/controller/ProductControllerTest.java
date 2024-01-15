package com.zalando.ECommerceManagement.userManagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zalando.ECommerceManagement.productManagement.controller.ProductController;
import com.zalando.ECommerceManagement.productManagement.model.Product;
import com.zalando.ECommerceManagement.productManagement.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productService;

    @Test
    @DisplayName("Should be able to get all the product")
    public void testGetAllProduct() throws Exception {
        List<Product> mockProduct = new ArrayList<>();
        Product product = new Product(1,
                "phone",
                499.99,
                "samsung 4.5 inches",
                10);
        mockProduct.add(product);
        //Second product
        Product product1 = new Product(2,
                "tv",
                400.99,
                "LG 29 inches",
                11);
        mockProduct.add(product1);
        when(productService.getAllProducts()).thenReturn(mockProduct);
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("phone"))
                .andExpect(jsonPath("$[0].price").value(499.99))
                .andExpect(jsonPath("$[0].description").value("samsung 4.5 inches"))
                .andExpect(jsonPath("$[0].stock").value(10))
                //Second product
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("tv"))
                .andExpect(jsonPath("$[1].price").value(400.99))
                .andExpect(jsonPath("$[1].description").value("LG 29 inches"))
                .andExpect(jsonPath("$[1].stock").value(11));

        verify(productService, times(1)).getAllProducts();
    }

    @Test
    @DisplayName("Should be able to get one product")
    public void testGetProductById() throws Exception {
        Product product = new Product(1,
                "phone",
                499.99,
                "samsung 4.5 inches",
                10);

        when(productService.getProductById(1)).thenReturn(product);

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("phone"))
                .andExpect(jsonPath("$.price").value(499.99))
                .andExpect(jsonPath("$.description").value("samsung 4.5 inches"))
                .andExpect(jsonPath("$.stock").value(10));

        verify(productService, times(1)).getProductById(1);
    }

    @Test
    @DisplayName("Should be able to get products by ids")
    public void testGetProductByIds() throws Exception {
        List<Product> mockProduct = new ArrayList<>();
        Product product = new Product(1,
                "phone",
                499.99,
                "samsung 4.5 inches",
                10);
        mockProduct.add(product);

        when(productService.getProductsByIds(List.of(1))).thenReturn(mockProduct);
        mockMvc.perform(get("/products/byIds?ids=1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("phone"))
                .andExpect(jsonPath("$[0].price").value(499.99))
                .andExpect(jsonPath("$[0].description").value("samsung 4.5 inches"))
                .andExpect(jsonPath("$[0].stock").value(10));

        verify(productService, times(1)).getProductsByIds(List.of(1));
    }

    @Test
    @DisplayName("Should be able to create a new product")
    public void createTestProduct() throws Exception {
        Product newproduct = new Product(1,
                "speaker"
                , 500.89,
                "jbl speaker go3",
                20);
        String jsonRequest = new ObjectMapper().writeValueAsString(newproduct);

        when(productService.createProduct(newproduct)).thenReturn(newproduct);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());

        verify(productService, times(1)).createProduct(any(Product.class));
    }
}
