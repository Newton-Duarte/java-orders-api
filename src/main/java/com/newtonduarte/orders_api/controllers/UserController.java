package com.newtonduarte.orders_api.controllers;

import com.newtonduarte.orders_api.config.SecurityConfig;
import com.newtonduarte.orders_api.domain.dto.CreateUserDto;
import com.newtonduarte.orders_api.domain.dto.UpdateUserDto;
import com.newtonduarte.orders_api.domain.dto.UserDto;
import com.newtonduarte.orders_api.domain.entities.UserEntity;
import com.newtonduarte.orders_api.mappers.UserMapper;
import com.newtonduarte.orders_api.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@SecurityRequirement(name = SecurityConfig.SECURITY)
@Tag(name = "Users", description = "Endpoints for User entity (requires auth)")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    @Operation(summary = "Get a list of users")
    @ApiResponse(responseCode = "200", description = "Request success")
    @ApiResponse(responseCode = "403", description = "Forbidden request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserEntity> users = userService.findAll();
        List<UserDto> usersDto = users.stream().map(userMapper::toDto).toList();
        return ResponseEntity.ok(usersDto);
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Get a single user passing by ID")
    @ApiResponse(responseCode = "200", description = "Request success")
    @ApiResponse(responseCode = "403", description = "Forbidden request")
    @ApiResponse(responseCode = "404", description = "User not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        Optional<UserEntity> foundUser = userService.findOne(id);

        return foundUser.map(userEntity -> {
            UserDto userDto = userMapper.toDto(userEntity);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @Operation(summary = "Create a single user passing the required fields")
    @ApiResponse(responseCode = "201", description = "User created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request body")
    @ApiResponse(responseCode = "403", description = "Forbidden request")
    @ApiResponse(responseCode = "409", description = "Email already exist")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody CreateUserDto createUserDto) {
        UserEntity createdUserEntity = userService.createUser(createUserDto);

        return new ResponseEntity<>(userMapper.toDto(createdUserEntity), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    @Operation(summary = "Update a single user passing the user id and the required fields")
    @ApiResponse(responseCode = "200", description = "User updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request body")
    @ApiResponse(responseCode = "403", description = "Forbidden request")
    @ApiResponse(responseCode = "409", description = "Email already exist")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUserDto updateUserDto) {
        if (!userService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        updateUserDto.setId(id);
        UserEntity savedUserEntity = userService.updateUser(id, updateUserDto);

        return new ResponseEntity<>(userMapper.toDto(savedUserEntity), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete a single user passing the user id")
    @ApiResponse(responseCode = "204", description = "User deleted successfully")
    @ApiResponse(responseCode = "403", description = "Forbidden request")
    @ApiResponse(responseCode = "404", description = "User not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (!userService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
