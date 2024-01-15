package com.zalando.ECommerceManagement.productManagement.repository;

import com.zalando.ECommerceManagement.productManagement.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository <Product , Integer> {
}
