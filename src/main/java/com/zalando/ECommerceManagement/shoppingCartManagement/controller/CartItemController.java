package com.zalando.ECommerceManagement.shoppingCartManagement.controller;

import com.zalando.ECommerceManagement.shoppingCartManagement.model.CartItem;
import com.zalando.ECommerceManagement.shoppingCartManagement.service.CartItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/cartItem")
public class CartItemController {
    private final CartItemService cartItemService;
    @Autowired

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping
    public CartItem addCartItem(@RequestBody @Valid CartItem cartItem){
        return cartItemService.createCartItem(cartItem);

    }

    @GetMapping
    public List<CartItem> getAllCartItem(){
        return cartItemService.getAllCartItem();
    }

    @DeleteMapping("/{id}")
    public void deleteCartItem (@PathVariable Integer id){
        cartItemService.deleteCartItem(id);
    }

}
