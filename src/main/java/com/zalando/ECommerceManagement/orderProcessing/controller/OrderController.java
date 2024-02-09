package com.zalando.ECommerceManagement.orderProcessing.controller;

import com.zalando.ECommerceManagement.orderProcessing.dto.OrderDetailDto;
import com.zalando.ECommerceManagement.orderProcessing.dto.OrderDto;
import com.zalando.ECommerceManagement.orderProcessing.model.Order;
import com.zalando.ECommerceManagement.orderProcessing.model.OrderStatus;
import com.zalando.ECommerceManagement.orderProcessing.service.OrderService;
import com.zalando.ECommerceManagement.productManagement.service.ProductService;
import com.zalando.ECommerceManagement.shoppingCartManagement.exception.CartNotFoundException;
import com.zalando.ECommerceManagement.shoppingCartManagement.model.Cart;
import com.zalando.ECommerceManagement.shoppingCartManagement.repository.CartRepository;
import com.zalando.ECommerceManagement.shoppingCartManagement.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final CartRepository cartRepository;

    @Autowired
    public OrderController(OrderService orderService, CartRepository cartRepository) {
        this.orderService = orderService;
        this.cartRepository = cartRepository;
    }

    @PostMapping
    public Integer OrderPlace(@RequestBody OrderDto orderDto) {
        Cart existingCart = cartRepository.findById(orderDto.getCardId()).orElseThrow(
                () -> new CartNotFoundException("Cart-ID", "Cart not found with ID : " + orderDto.getCardId()));

        Order newOrder = new Order();
        newOrder.setCart(existingCart);
        newOrder.setOrderStatus(OrderStatus.PLACED);
        Order newOrderInDb = orderService.createOrder(newOrder);
        return newOrderInDb.getId();
    }

    @GetMapping("/{id}")
    public OrderDetailDto orderDetailDto(@PathVariable Integer id) {
        return orderService.getOrderDetails(id);
    }
}

