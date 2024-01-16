package com.zalando.ECommerceManagement.productManagement.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductNotFoundException extends RuntimeException {
    private String errorKey;

    public ProductNotFoundException(String errorKey, String message) {
        super(message);
        this.errorKey = errorKey;
    }
}
