package com.zalando.ECommerceManagement.userManagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zalando.ECommerceManagement.shoppingCartManagement.controller.CartController;
import com.zalando.ECommerceManagement.shoppingCartManagement.model.Cart;
import com.zalando.ECommerceManagement.shoppingCartManagement.model.CartDto;
import com.zalando.ECommerceManagement.shoppingCartManagement.service.CartService;
import com.zalando.ECommerceManagement.userManagement.model.User;
import com.zalando.ECommerceManagement.userManagement.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static java.lang.reflect.Array.get;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CartController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CartControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CartService cartService;
    @MockBean
    private UserService userService;

    @Test
    @DisplayName("Should be able to create a new cart")
    public void createTestCart() throws Exception {
        CartDto cartDtoRequest = new CartDto(1, 1);
        String jsonRequest = new ObjectMapper().writeValueAsString(cartDtoRequest);
        Cart cart = new Cart(1, new User());
        User user = new User();
        when(cartService.createCart(any())).thenReturn(cart);
        when(userService.getUserById(1)).thenReturn(user);

        mockMvc.perform(post("/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());

        verify(cartService, times(1)).createCart(any(Cart.class));
        verify(userService, times(1)).getUserById(any(Integer.class));
    }

    @Test
    @DisplayName("Should be able to get all the Cart item")
    public void testGetALlCart() throws Exception {
        List<CartDto> mockCart = new ArrayList<>();
        CartDto cart1 = new CartDto(1, 1);
        mockCart.add(cart1);
        CartDto cart2 = new CartDto(2, 2);
        mockCart.add(cart2);

        when(cartService.getAllCart()).thenReturn(mockCart);

        mockMvc.perform(MockMvcRequestBuilders.get("/cart"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].userId").value(1))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].userId").value(2));

        verify(cartService, times(1)).getAllCart();
    }
}


