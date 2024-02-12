package com.zalando.ECommerceManagement.orderProcessing.service;

import com.zalando.ECommerceManagement.orderProcessing.dto.OrderDetailDto;
import com.zalando.ECommerceManagement.orderProcessing.dto.OrderDto;
import com.zalando.ECommerceManagement.orderProcessing.model.Order;
import com.zalando.ECommerceManagement.orderProcessing.model.OrderProduct;
import com.zalando.ECommerceManagement.orderProcessing.model.OrderStatus;
import com.zalando.ECommerceManagement.orderProcessing.repository.OrderProductRepository;
import com.zalando.ECommerceManagement.orderProcessing.repository.OrderRepository;
import com.zalando.ECommerceManagement.productManagement.model.Product;
import com.zalando.ECommerceManagement.productManagement.service.ProductService;
import com.zalando.ECommerceManagement.shoppingCartManagement.exception.CartNotFoundException;
import com.zalando.ECommerceManagement.shoppingCartManagement.model.Cart;
import com.zalando.ECommerceManagement.shoppingCartManagement.model.CartItem;
import com.zalando.ECommerceManagement.shoppingCartManagement.model.dto.ProductDto;
import com.zalando.ECommerceManagement.shoppingCartManagement.repository.CartItemRepository;
import com.zalando.ECommerceManagement.shoppingCartManagement.repository.CartRepository;
import com.zalando.ECommerceManagement.shoppingCartManagement.service.CartItemService;
import com.zalando.ECommerceManagement.userManagement.exception.UserNotFoundException;
import com.zalando.ECommerceManagement.userManagement.service.UserService;
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
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderProductRepository orderProductRepository;
    private final UserService userService;

    @Autowired
    public OrderService(OrderRepository orderRepository, CartItemService cartItemService, ProductService productService, CartRepository cartRepository, CartItemRepository cartItemRepository, OrderProductRepository orderProductRepository, UserService userService) {
        this.orderRepository = orderRepository;
        this.cartItemService = cartItemService;
        this.productService = productService;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.orderProductRepository = orderProductRepository;
        this.userService = userService;
    }

    public Order createNewOrder(OrderDto orderDto) {
        Cart existingCart = getCartById(orderDto.getCardId());
        Order newOrder = createOrderFromCart(existingCart);
        List<OrderProduct> orderProducts = createOrderProducts(newOrder, existingCart);
        newOrder.setUser(existingCart.getUser());
        saveOrderAndProducts(newOrder, orderProducts);
        clearCartItems(existingCart);
        return newOrder;
    }

    public Order getOrderById(Integer id) {
        return orderRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Order not found with ID:" + id)
        );
    }

    public OrderDetailDto getOrderDetails(Integer id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("Order-ID", "Order not found with ID : " + id)
        );
        OrderDetailDto orderDetailDto = new OrderDetailDto();
        orderDetailDto.setOrderId(id);
        orderDetailDto.setOrderStatus(order.getOrderStatus().name());
        List<ProductDto> productDtos = new ArrayList<>();
        Double totalPrice = 0.0;
        for (OrderProduct orderProduct : order.getProducts()) {
            ProductDto productDto = new ProductDto();
            productDto.setId(orderProduct.getProduct().getId());
            productDto.setName(orderProduct.getProduct().getName());
            productDto.setDescription(orderProduct.getProduct().getDescription());
            productDto.setPrice(orderProduct.getProduct().getPrice());
            productDtos.add(productDto);
            totalPrice += orderProduct.getProduct().getPrice();
        }
        orderDetailDto.setProducts(productDtos);
        orderDetailDto.setTotalPrice(totalPrice);
        return orderDetailDto;
    }

    public void save(Order order) {
        orderRepository.save(order);
    }

    private Cart getCartById(Integer cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart-ID", "Cart not found with ID : " + cartId));
    }

    private Order createOrderFromCart(Cart cart) {
        Order newOrder = new Order();
        newOrder.setCart(cart);
        newOrder.setOrderStatus(OrderStatus.PLACED);
        return orderRepository.save(newOrder);
    }

    private List<OrderProduct> createOrderProducts(Order order, Cart cart) {
        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());
        List<OrderProduct> orderProducts = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            Product product = productService.getProductById(cartItem.getProductId());
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrder(order);
            orderProduct.setProduct(product);
            orderProduct.setQuantity(cartItem.getQuantity());
            orderProducts.add(orderProduct);
        }
        return orderProducts;
    }

    private void saveOrderAndProducts(Order order, List<OrderProduct> orderProducts) {
        order.setProducts(orderProducts);
        orderProductRepository.saveAll(orderProducts);
        save(order);
    }

    private void clearCartItems(Cart cart) {
        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());
        cartItemRepository.deleteAll(cartItems);
    }
    public void updateOrderStatus(Integer orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new NoSuchElementException("Order not found with ID:" + orderId)
        );

        order.setOrderStatus(status);
        orderRepository.save(order);
    }
}
