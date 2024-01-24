package com.zalando.ECommerceManagement.userManagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "The Name should not be Null.")
    private String name;
    @NotNull(message = "The Email should not be Null.")
    private String email;
    @NotNull(message = "The Password should not Null.")
    private String password;


}
