package com.newtonduarte.orders_api.services;

import com.newtonduarte.orders_api.domain.dto.CreateUserDto;
import com.newtonduarte.orders_api.domain.dto.UpdateUserDto;
import com.newtonduarte.orders_api.domain.entities.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserEntity> findAll();
    UserEntity createUser(CreateUserDto createUserDto);
    UserEntity updateUser(Long id, UpdateUserDto updateUserDto);
    Optional<UserEntity> findOne(Long id);
    boolean isExists(Long id);
    void delete(Long id);
}
