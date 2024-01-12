package com.zalando.ECommerceManagement.userManagement.repository;

import com.zalando.ECommerceManagement.userManagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository <User, Integer> {
}
