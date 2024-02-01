package com.zalando.ECommerceManagement.shoppingCartManagement.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemNotFoundException extends RuntimeException{
    private String errorKey;

    public CartItemNotFoundException(String errorKey, String message) {
        super(message);
        this.errorKey = errorKey;
    }
}
