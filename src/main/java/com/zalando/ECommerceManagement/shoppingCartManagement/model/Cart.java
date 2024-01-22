package com.zalando.ECommerceManagement.shoppingCartManagement.model;

import com.zalando.ECommerceManagement.userManagement.model.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

public class Cart {
private Integer id;
@OneToOne
@JoinColumn(name = "user_id", referencedColumnName = "id")
private User user;

}
