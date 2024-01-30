package com.zalando.ECommerceManagement.shoppingCartManagement.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    @NotNull(message = "The id should not be null")
    private Integer id;
    @NotNull(message = "The userId should not be null")
    private Integer userId;
}
