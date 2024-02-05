package com.zalando.ECommerceManagement.userManagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zalando.ECommerceManagement.shoppingCartManagement.controller.CartController;
import com.zalando.ECommerceManagement.shoppingCartManagement.model.Cart;
import com.zalando.ECommerceManagement.shoppingCartManagement.model.dto.CartDto;
import com.zalando.ECommerceManagement.shoppingCartManagement.service.CartService;
import com.zalando.ECommerceManagement.userManagement.model.User;
import com.zalando.ECommerceManagement.userManagement.service.UserService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

    @Test
    @DisplayName("Should be able to get one product")
    public void testGetProductById() throws Exception {

        CartDto cartDto = new CartDto(1, 1);

        when(cartService.getCartById(1)).thenReturn(cartDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/cart/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.userId").value(1));

        verify(cartService, times(1)).getCartById(1);
    }

    @Test
    @DisplayName("Should be able to delete a cart by ID")
    public void testDeleteCartById() throws Exception {
        doNothing().when(cartService).deleteCart(1);

        mockMvc.perform(delete("/cart/{id}", 1))
                .andExpect(status().isOk());

        verify(cartService, times(1)).deleteCart(1);
    }

    @ParameterizedTest
    @MethodSource("getCartInput")
    @DisplayName("Should not create a new product with null fields")
    public void testInvalidCartFields(
            CartDto newCartDto,
            String errorKey,
            String errorMessage) throws Exception {
        String jsonRequest = new ObjectMapper().writeValueAsString(newCartDto);
        mockMvc.perform(post("/cart").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$." + errorKey).value(errorMessage));

        verify(cartService, times(0)).createCart(any(Cart.class));
    }

    private static Stream<Arguments> getCartInput() {
        return Stream.of(Arguments.of(new CartDto(
                                null, 2),
                        "id", "The id should not be null"),
                Arguments.of(new CartDto(1, null
                        ),
                        "userId", "The userId should not be null"));
    }

}


