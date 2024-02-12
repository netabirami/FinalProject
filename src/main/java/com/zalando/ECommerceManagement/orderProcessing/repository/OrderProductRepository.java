package com.zalando.ECommerceManagement.orderProcessing.repository;

import com.zalando.ECommerceManagement.orderProcessing.model.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepository extends JpaRepository <OrderProduct,Integer> {
}
