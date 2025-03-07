package com.newtonduarte.orders_api.services;

import com.newtonduarte.orders_api.domain.entities.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserEntity> findAll();
    UserEntity save(UserEntity userEntity);
    Optional<UserEntity> findOne(Long id);
    boolean isExists(Long id);
    UserEntity partialUpdate(Long id, UserEntity userEntity);
    void delete(Long id);
}
