package com.zalando.ECommerceManagement.shoppingCartManagement.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartViewDto {
    private Integer cartId;
    private Double totalPrice;
    private List<ProductDto> items;

}
