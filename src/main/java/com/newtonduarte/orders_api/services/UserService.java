package com.newtonduarte.orders_api.services;

import com.newtonduarte.orders_api.domain.dto.CreateUserDto;
import com.newtonduarte.orders_api.domain.dto.UpdateUserDto;
import com.newtonduarte.orders_api.domain.dto.UpdateUserProfileDto;
import com.newtonduarte.orders_api.domain.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Page<UserEntity> findAll(Pageable pageable, String search);
    UserEntity createUser(CreateUserDto createUserDto);
    UserEntity updateUser(Long id, UpdateUserDto updateUserDto);
    UserEntity updateUserProfile(Long id, UpdateUserProfileDto updateUserProfileDto);
    Optional<UserEntity> findOne(Long id);
    boolean isExists(Long id);
    void delete(Long id);
}
