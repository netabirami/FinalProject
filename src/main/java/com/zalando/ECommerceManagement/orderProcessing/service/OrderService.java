package com.zalando.ECommerceManagement.orderProcessing.service;

import com.zalando.ECommerceManagement.orderProcessing.dto.OrderDetailDto;
import com.zalando.ECommerceManagement.orderProcessing.model.Order;
import com.zalando.ECommerceManagement.orderProcessing.repository.OrderRepository;
import com.zalando.ECommerceManagement.productManagement.model.Product;
import com.zalando.ECommerceManagement.productManagement.service.ProductService;
import com.zalando.ECommerceManagement.shoppingCartManagement.model.CartItem;
import com.zalando.ECommerceManagement.shoppingCartManagement.model.dto.ProductDto;
import com.zalando.ECommerceManagement.shoppingCartManagement.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartItemService cartItemService;
    private final ProductService productService;
    @Autowired
    public OrderService(OrderRepository orderRepository, CartItemService cartItemService, ProductService productService) {
        this.orderRepository = orderRepository;
        this.cartItemService = cartItemService;
        this.productService = productService;
    }
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order getOrderById(Integer id) {
        return orderRepository.findById(id).orElseThrow(
                ()-> new NoSuchElementException("Order not found with ID:"+id)
        );
    }

    public OrderDetailDto getOrderDetails(Integer id) {
        Order order = getOrderById(id);
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
