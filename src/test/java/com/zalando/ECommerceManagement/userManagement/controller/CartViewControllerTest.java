package com.zalando.ECommerceManagement.userManagement.controller;

import com.zalando.ECommerceManagement.productManagement.model.Product;
import com.zalando.ECommerceManagement.productManagement.service.ProductService;
import com.zalando.ECommerceManagement.shoppingCartManagement.controller.cartView.CartViewController;
import com.zalando.ECommerceManagement.shoppingCartManagement.model.CartItem;
import com.zalando.ECommerceManagement.shoppingCartManagement.service.CartItemService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(CartViewController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CartViewControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CartItemService cartItemService;
    @MockBean
    private ProductService productService;

    @Test
    @DisplayName("Should be able to get all CartItem")
    public void testGetAllCartItem() throws Exception {
        List<CartItem> mockCartItem = new ArrayList<>();
        CartItem cartItem = new CartItem(1, 1, 1, 10);
        mockCartItem.add(cartItem);
        when(cartItemService.getCartItemsById(1)).thenReturn(mockCartItem);

        List<Product> mockProduct = new ArrayList<>();
        Product mockProducts = new Product(1, "phone", 222.00, "12inch phone", 10);
        mockProduct.add(mockProducts);
        when(productService.getProductsByIds(List.of(1))).thenReturn(mockProduct);

        mockMvc.perform(get("/cartView/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.cartId").value(1))
                .andExpect(jsonPath("$.totalPrice").value(222.00))
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items[0].id").value(1))
                .andExpect(jsonPath("$.items[0].name").value("phone"))
                .andExpect(jsonPath("$.items[0].price").value(222.00))
                .andExpect(jsonPath("$.items[0].description").value("12inch phone"));

        verify(cartItemService, times(1)).getCartItemsById(1);
        verify(productService, times(1)).getProductsByIds(List.of(1));
    }
}
