package com.zalando.ECommerceManagement.orderProcessing.exception;

import com.zalando.ECommerceManagement.productManagement.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class OrderGlobalExceptionHandler {
    @ExceptionHandler({OrderNotFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleOrderNotFoundException(OrderNotFoundException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put(ex.getErrorKey(), ex.getMessage());
        return errors;
    }
}
