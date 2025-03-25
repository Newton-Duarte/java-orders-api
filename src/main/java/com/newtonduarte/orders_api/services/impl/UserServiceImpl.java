package com.newtonduarte.orders_api.services.impl;

import com.newtonduarte.orders_api.domain.dto.CreateUserDto;
import com.newtonduarte.orders_api.domain.dto.UpdateUserDto;
import com.newtonduarte.orders_api.domain.dto.UpdateUserProfileDto;
import com.newtonduarte.orders_api.domain.entities.UserEntity;
import com.newtonduarte.orders_api.repositories.UserRepository;
import com.newtonduarte.orders_api.security.StaticPasswordEncoder;
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
        newUser.setPassword(StaticPasswordEncoder.encodePassword(createUserDto.getPassword()));

        return userRepository.save(newUser);
    }

    @Override
    public UserEntity updateUser(Long id, UpdateUserDto updateUserDto) {
        UserEntity existingUser = userRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id" + id));

        Optional<UserEntity> existingUserByEmail = userRepository
                .findByEmail(updateUserDto.getEmail());

        if (existingUserByEmail.isPresent() && !Objects.equals(existingUserByEmail.get().getId(), updateUserDto.getId())) {
            throw new IllegalStateException("User with same email already exists");
        }

        existingUser.setId(id);
        existingUser.setName(updateUserDto.getName());
        existingUser.setEmail(updateUserDto.getEmail());

        if (updateUserDto.getPassword() != null) {
            existingUser.setPassword(StaticPasswordEncoder.encodePassword(updateUserDto.getPassword()));
        }

        return userRepository.save(existingUser);
    }

    @Override
    public UserEntity updateUserProfile(Long userId, UpdateUserProfileDto updateUserProfileDto) {
        UserEntity existingUser = userRepository
                .findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id" + userId));

        Optional<UserEntity> existingUserByEmail = userRepository
                .findByEmail(updateUserProfileDto.getEmail());

        if (existingUserByEmail.isPresent() && !Objects.equals(existingUserByEmail.get().getId(), userId)) {
            throw new IllegalStateException("User with same email already exists");
        }

        existingUser.setId(userId);
        existingUser.setName(updateUserProfileDto.getName());
        existingUser.setEmail(updateUserProfileDto.getEmail());

        if (updateUserProfileDto.getPassword() != null) {
            existingUser.setPassword(StaticPasswordEncoder.encodePassword(updateUserProfileDto.getPassword()));
        }

        return userRepository.save(existingUser);
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
