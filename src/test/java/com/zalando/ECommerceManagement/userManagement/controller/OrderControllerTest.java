package com.zalando.ECommerceManagement.userManagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zalando.ECommerceManagement.orderProcessing.controller.OrderController;
import com.zalando.ECommerceManagement.orderProcessing.dto.OrderDetailDto;
import com.zalando.ECommerceManagement.orderProcessing.dto.OrderDto;
import com.zalando.ECommerceManagement.orderProcessing.model.Order;
import com.zalando.ECommerceManagement.orderProcessing.model.OrderStatus;
import com.zalando.ECommerceManagement.orderProcessing.service.OrderService;
import com.zalando.ECommerceManagement.shoppingCartManagement.model.Cart;
import com.zalando.ECommerceManagement.shoppingCartManagement.model.dto.ProductDto;
import com.zalando.ECommerceManagement.shoppingCartManagement.repository.CartRepository;
import com.zalando.ECommerceManagement.userManagement.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc(addFilters = false)
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OrderService orderService;
    @MockBean
    private CartRepository cartRepository;

    @Test
    @DisplayName("Should be able to create a new Order")
    public void createTestOrder() throws Exception {
        ProductDto newProductDto = new ProductDto(
                1, "IPhone", 300.0, "13 inches, 8GB");

        OrderDto orderDto = new OrderDto(
                1
        );
        String postRequestBody = new ObjectMapper().writeValueAsString(orderDto);

        Cart cart = new Cart(1, new User());
        Order order = new Order(1,cart,OrderStatus.PLACED, List.of(), null);
        when(cartRepository.findById(any())).thenReturn(Optional.of(cart));
        when(orderService.createNewOrder(any())).thenReturn(order);

        mockMvc.perform(post("/order/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postRequestBody))
                .andExpect(status().isOk());

        verify(orderService, times(1)).createNewOrder(any(OrderDto.class));
        verify(cartRepository, times(1)).findById(any(Integer.class));
    }

    @Test
    @DisplayName("Should be able to Order Detail")
    public void testGetOrderById() throws Exception {
        ProductDto ProductDto = new ProductDto(
                1, "IPhone", 300.0, "13 inches, 8GB");
        OrderDetailDto orderDetailDto = new OrderDetailDto(1,
                List.of(ProductDto),
                300.0,
                "PLACED");

        when(orderService.getOrderDetails(1)).thenReturn(orderDetailDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/order/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.orderId").value(1))
                .andExpect(jsonPath("$.products").isArray())
                .andExpect(jsonPath("$.products[0].id").value(1))
                .andExpect(jsonPath("$.products[0].name").value("IPhone"))
                .andExpect(jsonPath("$.products[0].price").value(300.0))
                .andExpect(jsonPath("$.products[0].description").value("13 inches, 8GB"))
                .andExpect(jsonPath("$.totalPrice").value(300.0))
                .andExpect(jsonPath("$.orderStatus").value("PLACED"));

        verify(orderService, times(1)).getOrderDetails(1);
    }
}
