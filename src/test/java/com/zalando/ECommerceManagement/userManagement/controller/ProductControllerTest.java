package com.zalando.ECommerceManagement.userManagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zalando.ECommerceManagement.productManagement.controller.ProductController;
import com.zalando.ECommerceManagement.productManagement.model.Product;
import com.zalando.ECommerceManagement.productManagement.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
                "jbl speaker go 3",
                20);
        String jsonRequest = new ObjectMapper().writeValueAsString(newproduct);

        when(productService.createProduct(newproduct)).thenReturn(newproduct);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());

        verify(productService, times(1)).createProduct(any(Product.class));
    }

    @Test
    @DisplayName("Should be able to update an existing Product")
    public void testUpdateProduct() throws Exception {
        Product existingProduct = new Product(
                1,
                "micro oven",
                599.99,
                "450 watt",
                10);
        when(productService.getProductById(1)).thenReturn(existingProduct);
        when(productService.updateProduct(existingProduct)).thenReturn(existingProduct);
        existingProduct.setName("micro oven");
        existingProduct.setPrice(599.99);
        existingProduct.setDescription("450 watt");
        existingProduct.setStock(10);

        String jsonRequest = new ObjectMapper().writeValueAsString(existingProduct);
        mockMvc.perform(put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());

        verify(productService, times(1)).updateProduct(any(Product.class));
    }

    @Test
    @DisplayName("Should be able to delete a product by ID")
    public void testDeleteProductById() throws Exception {
        doNothing().when(productService).deleteProduct(1);

        mockMvc.perform(delete("/products/{id}", 1))
                .andExpect(status().isOk());

        verify(productService, times(1)).deleteProduct(1);
    }

    @ParameterizedTest
    @MethodSource("getProductInput")
    @DisplayName("Should not create a new product with null fields")
    public void testInvalidProductFields(
            Product newProduct,
            String errorKey,
            String errorMessage) throws Exception {
        String jsonRequest = new ObjectMapper().writeValueAsString(newProduct);
        mockMvc.perform(post("/products").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$." + errorKey).value(errorMessage));

        verify(productService, times(0)).createProduct(any(Product.class));
    }

    @ParameterizedTest
    @MethodSource("getUpDatedProductInput")
    @DisplayName("Should not update a product with null fields")
    public void testInvalidUpDateProduct(
            Product updateProduct,
            String errorKey,
            String errorMessage) throws Exception {
        String jsonRequest = new ObjectMapper().writeValueAsString(updateProduct);
        mockMvc.perform(put("/products/{id}", updateProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$." + errorKey).value(errorMessage));

        verify(productService, times(0)).createProduct(any(Product.class));
    }
    private static Stream<Arguments> getUpDatedProductInput() {
        return Stream.of(Arguments.of(new Product(
                                1,//ID is expected to be generated by the system
                                null,
                                200.99,
                                "smart Phone 45 pix",
                                14),
                        "name", "The name should not be null"),
                Arguments.of(new Product(
                                1,
                                "Phone",
                                null,
                                "smart phone",
                                10),
                        "price", "The price should not be null"),
                Arguments.of(new Product(
                                1,
                                "phone",
                                200.77,
                                null,
                                15),
                        "description", "The description should not be null"),
                Arguments.of(new Product(
                                1,
                                "T.V",
                                300.77,
                                "24 inch coolerT.V",
                                null),
                        "stock", "The stock should not be null"));
    }
    private static Stream<Arguments> getProductInput() {
        return Stream.of(Arguments.of(new Product(
                                null,//ID is expected to be generated by the system
                                null,
                                200.99,
                                "smart Phone 45 pix",
                                14),
                        "name", "The name should not be null"),
                Arguments.of(new Product(
                                null,
                                "Phone",
                                null,
                                "smart phone",
                                10),
                        "price", "The price should not be null"),
                Arguments.of(new Product(null,
                                "phone",
                                200.77,
                                null,
                                15),
                        "description", "The description should not be null"),
                Arguments.of(new Product(null,
                                "T.V",
                                300.77,
                                "24 inch coolerT.V",
                                null),
                        "stock", "The stock should not be null"));
    }
}
