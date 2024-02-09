package com.zalando.ECommerceManagement.orderProcessing.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



public enum OrderStatus {
    PLACED,
    SHIPPED,
    DELIVERED;
}
