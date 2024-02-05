package com.zalando.ECommerceManagement.shoppingCartManagement.repository;

import com.zalando.ECommerceManagement.shoppingCartManagement.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository <CartItem, Integer> {
    List<CartItem> findByCartId(Integer id);
}
