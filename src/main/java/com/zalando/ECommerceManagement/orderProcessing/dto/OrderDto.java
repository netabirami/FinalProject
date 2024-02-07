package com.zalando.ECommerceManagement.orderProcessing.dto;

import com.zalando.ECommerceManagement.orderProcessing.model.OrderStatus;
import com.zalando.ECommerceManagement.shoppingCartManagement.model.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDto {
    private Integer cardId;
    private OrderStatus orderStatus;
    private List<ProductDto> products;
    private Double totalPrice;
}
