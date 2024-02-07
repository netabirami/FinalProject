package com.zalando.ECommerceManagement.orderProcessing.repository;

import com.zalando.ECommerceManagement.orderProcessing.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface OrderRepository extends JpaRepository <Order,Integer> {
}
