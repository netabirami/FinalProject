package com.zalando.ECommerceManagement.shoppingCartManagement.service;

import com.zalando.ECommerceManagement.shoppingCartManagement.exception.CartNotFoundException;
import com.zalando.ECommerceManagement.shoppingCartManagement.model.Cart;
import com.zalando.ECommerceManagement.shoppingCartManagement.model.dto.CartDto;
import com.zalando.ECommerceManagement.shoppingCartManagement.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    private final CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Cart createCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public List<CartDto> getAllCart() {
        List<Cart> carts = cartRepository.findAll();
        List<CartDto> cartDtos = new ArrayList<>();
        for (Cart cart : carts) {
            cartDtos.add(new CartDto(cart.getId(), cart.getUser().getId()));
        }
        return cartDtos;
    }

    public CartDto getCartById(Integer id) {
       Cart existingCart = cartRepository.findById(id).orElseThrow(
                () -> new CartNotFoundException("Cart-ID", "Cart not found with ID : " + id));
       CartDto cartDto = new CartDto(existingCart.getId(),existingCart.getUser().getId());
       return cartDto;

    }
    public void deleteCart(Integer id) {
        cartRepository.deleteById(id);
    }


}

