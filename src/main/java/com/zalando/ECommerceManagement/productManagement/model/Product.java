package com.zalando.ECommerceManagement.productManagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "The name should not be null")
    private String name;
    @NotNull(message = "The price should not be null")
    private Double price;
    @NotNull(message = "The description should not be null")
    private String description;
    @NotNull(message = "The stock should not be null")
    private Integer stock;
}
