package com.zalando.ECommerceManagement.orderProcessing.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderNotFoundException extends RuntimeException{
    private String errorKey;

    public OrderNotFoundException(String errorKey, String message) {
        super(message);
        this.errorKey = errorKey;
    }


    public String getErrorKey() {
        return errorKey;
    }
}
