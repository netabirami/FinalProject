package com.zalando.ECommerceManagement.shoppingCartManagement.model;

import com.zalando.ECommerceManagement.productManagement.model.Product;
import com.zalando.ECommerceManagement.userManagement.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;


    public List<Product> getProducts() {
        return null;
    }
}
