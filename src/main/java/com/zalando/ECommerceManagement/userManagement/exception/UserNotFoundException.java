package com.zalando.ECommerceManagement.userManagement.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserNotFoundException extends RuntimeException {
    private String errorKey;

    public UserNotFoundException(String errorKey, String message) {
        super(message);
        this.errorKey = errorKey;
    }
}
