package com.zalando.ECommerceManagement.orderProcessing.controller;

import com.zalando.ECommerceManagement.orderProcessing.dto.OrderDetailDto;
import com.zalando.ECommerceManagement.orderProcessing.dto.OrderDto;
import com.zalando.ECommerceManagement.orderProcessing.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@Validated
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/checkout")
    public Integer placeOrder(@RequestBody OrderDto orderDto) {
        return orderService.createNewOrder(orderDto).getId();
    }

    @GetMapping("/{id}")
    public OrderDetailDto orderDetailDto(@PathVariable Integer id) {
        return orderService.getOrderDetails(id);
    }
}

