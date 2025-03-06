package com.newtonduarte.orders_api.controllers;

import com.newtonduarte.orders_api.domain.UserEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @GetMapping(path = "users")
    public UserEntity getUsers() {
        return UserEntity.builder()
                .id(1L)
                .name("Newton Duarte")
                .email("newton@email.com")
                .build();
    }
}
