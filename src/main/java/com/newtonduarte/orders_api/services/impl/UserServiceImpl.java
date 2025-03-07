package com.newtonduarte.orders_api.services.impl;

import com.newtonduarte.orders_api.domain.entities.UserEntity;
import com.newtonduarte.orders_api.repositories.UserRepository;
import com.newtonduarte.orders_api.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserEntity> findAll() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    @Override
    public Optional<UserEntity> findOne(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public UserEntity partialUpdate(Long id, UserEntity userEntity) {
        userEntity.setId(id);

        return userRepository.findById(id).map(existingAuthor -> {
            Optional.ofNullable(userEntity.getName()).ifPresent(existingAuthor::setName);
            Optional.ofNullable(userEntity.getEmail()).ifPresent(existingAuthor::setEmail);
            return userRepository.save(existingAuthor);
        }).orElseThrow(() -> new RuntimeException("User does not exist"));
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
