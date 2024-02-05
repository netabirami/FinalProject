package com.zalando.ECommerceManagement.shoppingCartManagement.service;

import com.zalando.ECommerceManagement.shoppingCartManagement.exception.CartItemNotFoundException;
import com.zalando.ECommerceManagement.shoppingCartManagement.model.CartItem;
import com.zalando.ECommerceManagement.shoppingCartManagement.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemService {
    private final CartItemRepository cartItemRepository;

    @Autowired
    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public  List<CartItem> getAllCartItem() {
        return cartItemRepository.findAll();
    }

    public CartItem createCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    public void deleteCartItem(Integer id) {
        cartItemRepository.deleteById(id);

    }

    public CartItem getCartItemById(Integer id) {
        return cartItemRepository.findById(id).orElseThrow(
                ()-> new CartItemNotFoundException("CartItem - Id", "CartItem not found with ID:" +id)
        );
    }
    public List<CartItem> getCartItemsById(Integer id) {
        return cartItemRepository.findByCartId(id);
    }

    public CartItem updateCartItem(CartItem existingCartItem) {
        return cartItemRepository.save(existingCartItem);
    }
}
