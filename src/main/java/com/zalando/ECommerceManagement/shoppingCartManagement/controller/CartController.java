package com.zalando.ECommerceManagement.shoppingCartManagement.controller;

import com.zalando.ECommerceManagement.shoppingCartManagement.model.Cart;
import com.zalando.ECommerceManagement.shoppingCartManagement.model.dto.CartDto;
import com.zalando.ECommerceManagement.shoppingCartManagement.service.CartService;
import com.zalando.ECommerceManagement.userManagement.model.User;
import com.zalando.ECommerceManagement.userManagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final UserService userService;

    @Autowired
    public CartController(
            CartService cartService,
            UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @PostMapping
    public CartDto addCart(@RequestBody @Valid CartDto cartDto) {
        User existingUser = userService.getUserById(cartDto.getUserId());
        Cart newCart = new Cart();
        newCart.setUser(existingUser);
        Cart cartFromDb = cartService.createCart(newCart);
        return new CartDto(cartFromDb.getId(), cartDto.getUserId());
    }

    @GetMapping
    public List<CartDto> getAllCart() {
        return cartService.getAllCart();
    }

    @GetMapping("/{id}")
    public CartDto getCartById(@PathVariable Integer id) {
        return cartService.getCartById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteCart(@PathVariable Integer id) {
        cartService.deleteCart(id);
    }
}
