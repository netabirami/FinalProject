package com.zalando.ECommerceManagement.userManagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zalando.ECommerceManagement.shoppingCartManagement.controller.CartItemController;
import com.zalando.ECommerceManagement.shoppingCartManagement.model.CartItem;
import com.zalando.ECommerceManagement.shoppingCartManagement.service.CartItemService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(CartItemController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CartItemControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CartItemService cartItemService;

    @Test
    @DisplayName("Should be able to create a new CartItem")
    public void testCreateCartItem() throws Exception {
        CartItem newCartItem = new CartItem(1, 1, 1, 10);
        String jsonRequest = new ObjectMapper().writeValueAsString(newCartItem);

        when(cartItemService.createCartItem(newCartItem)).thenReturn(newCartItem);

        mockMvc.perform(post("/cartItem")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());

        verify(cartItemService, times(1)).createCartItem(any(CartItem.class));

    }

    @Test
    @DisplayName("should be able to get all the CartItem")
    public void testGetAllCartItem() throws Exception {
        List<CartItem> mockCartItem = new ArrayList<>();
        CartItem cartItem = new CartItem(1, 1, 1, 10);
        mockCartItem.add(cartItem);
        //second cartItem
        CartItem cartItem1 = new CartItem(2, 2, 2, 10);
        mockCartItem.add(cartItem1);
        when(cartItemService.getAllCartItem()).thenReturn(mockCartItem);
        mockMvc.perform(get("/cartItem"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].cartId").value(1))
                .andExpect(jsonPath("$[0].productId").value(1))
                .andExpect(jsonPath("$[0].quantity").value(10))
                //second cartItem
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].cartId").value(2))
                .andExpect(jsonPath("$[1].productId").value(2))
                .andExpect(jsonPath("$[1].quantity").value(10));

        verify(cartItemService, times(1)).getAllCartItem();
    }
    @Test
    @DisplayName("Should be able to delete a product by ID")
    public void testDeleteCartItem() throws Exception {
        doNothing().when(cartItemService).deleteCartItem(1);

        mockMvc.perform(delete("/cartItem/{id}", 1))
                .andExpect(status().isOk());

        verify(cartItemService, times(1)).deleteCartItem(1);
    }

    @Test
    @DisplayName("Should be able to update an Existing Quanity")
    public void testUpdateCartItem()throws Exception{
        CartItem existingCartItem = new CartItem(1,1,1,10);
        when (cartItemService.getCartItemById(1)).thenReturn(existingCartItem);
        when(cartItemService.updateCartItem(existingCartItem)).thenReturn(existingCartItem);
        existingCartItem.setQuantity(11);
        String jsonRequest = new ObjectMapper().writeValueAsString(existingCartItem);
        mockMvc.perform(put("/cartItem/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());

        verify(cartItemService, times(1)).updateCartItem(any(CartItem.class));

    }

}

