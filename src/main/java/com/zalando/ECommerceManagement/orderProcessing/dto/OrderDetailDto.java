package com.zalando.ECommerceManagement.orderProcessing.dto;

import com.zalando.ECommerceManagement.productManagement.model.Product;
import com.zalando.ECommerceManagement.shoppingCartManagement.model.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDto {
    private Integer orderId;
    private List <ProductDto> products;
    private  Double totalPrice;
    private String orderStatus;
}
