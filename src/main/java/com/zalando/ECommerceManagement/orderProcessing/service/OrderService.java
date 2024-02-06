package com.zalando.ECommerceManagement.orderProcessing.service;

import com.zalando.ECommerceManagement.orderProcessing.dto.OrderDto;
import com.zalando.ECommerceManagement.orderProcessing.model.Order;
import com.zalando.ECommerceManagement.orderProcessing.repository.OrderRepository;
import com.zalando.ECommerceManagement.productManagement.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;


@Service
public class OrderService {
    @Autowired
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }


    public Order getOrderById(Integer id) {
        return orderRepository.findById(id).orElseThrow(
                ()-> new NoSuchElementException("Order not found with ID:"+id)
        );
    }
}
