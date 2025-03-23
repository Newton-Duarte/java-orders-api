package com.newtonduarte.orders_api.services.impl;

import com.newtonduarte.orders_api.domain.dto.CreateUserDto;
import com.newtonduarte.orders_api.domain.dto.UpdateUserDto;
import com.newtonduarte.orders_api.domain.entities.UserEntity;
import com.newtonduarte.orders_api.repositories.UserRepository;
import com.newtonduarte.orders_api.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<UserEntity> findAll() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public UserEntity createUser(CreateUserDto createUserDto) {
        Optional<UserEntity> existingUser = userRepository
                .findByEmail(createUserDto.getEmail());

        if (existingUser.isPresent()) {
            throw new IllegalStateException("User with same email already exists");
        }

        UserEntity newUser = new UserEntity();
        newUser.setName(createUserDto.getName());
        newUser.setEmail(createUserDto.getEmail());

        return userRepository.save(newUser);
    }

    @Override
    public UserEntity updateUser(Long id, UpdateUserDto updateUserDto) {
        UserEntity existingUser = userRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id" + id));

        Optional<UserEntity> existingUserByEmail = userRepository
                .findByEmail(updateUserDto.getEmail());

        if (existingUserByEmail.isPresent()) {
            if (!Objects.equals(existingUserByEmail.get().getId(), updateUserDto.getId())) {
                throw new IllegalStateException("User with same email already exists");
            }
        }

        UserEntity newUser = new UserEntity();
        newUser.setId(updateUserDto.getId());
        newUser.setName(updateUserDto.getName());
        newUser.setEmail(updateUserDto.getEmail());

        return userRepository.save(newUser);
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
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
