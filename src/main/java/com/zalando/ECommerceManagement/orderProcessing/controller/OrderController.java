package com.zalando.ECommerceManagement.orderProcessing.controller;

import com.zalando.ECommerceManagement.orderProcessing.dto.OrderDetailDto;
import com.zalando.ECommerceManagement.orderProcessing.dto.OrderDto;
import com.zalando.ECommerceManagement.orderProcessing.model.Order;
import com.zalando.ECommerceManagement.orderProcessing.model.OrderStatus;
import com.zalando.ECommerceManagement.orderProcessing.service.OrderService;
import com.zalando.ECommerceManagement.productManagement.model.Product;
import com.zalando.ECommerceManagement.productManagement.service.ProductService;
import com.zalando.ECommerceManagement.shoppingCartManagement.exception.CartNotFoundException;
import com.zalando.ECommerceManagement.shoppingCartManagement.model.Cart;
import com.zalando.ECommerceManagement.shoppingCartManagement.model.CartItem;
import com.zalando.ECommerceManagement.shoppingCartManagement.model.dto.ProductDto;
import com.zalando.ECommerceManagement.shoppingCartManagement.repository.CartRepository;
import com.zalando.ECommerceManagement.shoppingCartManagement.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Validated
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private final OrderService orderService;

    @Autowired
    private final CartRepository cartRepository;
    @Autowired
    private final ProductService productService;
    @Autowired
    private final CartItemService cartItemService;

    @Autowired
    public OrderController(OrderService orderService, CartRepository cartRepository, ProductService productService, CartItemService cartItemService) {
        this.orderService = orderService;
        this.cartRepository = cartRepository;
        this.productService = productService;
        this.cartItemService = cartItemService;
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
        Order order = orderService.getOrderById(id);
        OrderDetailDto orderDetailDto = new OrderDetailDto();
        orderDetailDto.setOrderId(id);
        orderDetailDto.setOrderStatus(order.getOrderStatus().name());

        List<CartItem> cartItems = cartItemService.getCartItemsById(id);
        List<Integer> productIds = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            productIds.add(cartItem.getProductId());
        }
        List<Product> products = productService.getProductsByIds(productIds);
        List<ProductDto> productDtos = new ArrayList<>();
        Double totalPrice = 0.0;

        for (Product product : products) {
            ProductDto productDto = new ProductDto();
            productDto.setId(product.getId());
            productDto.setName(product.getName());
            productDto.setPrice(product.getPrice());
            productDto.setDescription(product.getDescription());
            productDtos.add(productDto);
            totalPrice += product.getPrice();
        }
        orderDetailDto.setProducts(productDtos);
        orderDetailDto.setTotalPrice(totalPrice);
        return orderDetailDto;
    }
}

