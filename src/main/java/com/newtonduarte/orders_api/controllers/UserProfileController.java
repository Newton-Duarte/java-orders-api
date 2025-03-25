package com.newtonduarte.orders_api.controllers;

import com.newtonduarte.orders_api.config.SecurityConfig;
import com.newtonduarte.orders_api.domain.dto.ApiErrorResponse;
import com.newtonduarte.orders_api.domain.dto.UpdateUserProfileDto;
import com.newtonduarte.orders_api.domain.dto.UserDto;
import com.newtonduarte.orders_api.domain.entities.UserEntity;
import com.newtonduarte.orders_api.mappers.UserMapper;
import com.newtonduarte.orders_api.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = SecurityConfig.SECURITY)
@RequestMapping(path = "/user-profile")
@Tag(name = "User Profile", description = "Endpoints for User Profile (requires auth)")
public class UserProfileController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    @Operation(summary = "Get current user profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request success"),
            @ApiResponse(responseCode = "403", description = "Forbidden request (Requires auth)", content = {
                    @Content(schema = @Schema(implementation = Void.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
            })
    })
    public ResponseEntity<UserDto> getUserProfile(@RequestAttribute Long userId) {
        UserEntity user = userService
                .findOne(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID " + userId));

        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @PutMapping
    @Operation(summary = "Update current user profile")
    public ResponseEntity<UserDto> updateUserProfile(
            @Valid @RequestBody UpdateUserProfileDto updateUserProfileDto,
            @RequestAttribute Long userId
    ) {
        userService
                .findOne(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID " + userId));

        UserEntity updatedUserEntity = userService.updateUserProfile(userId, updateUserProfileDto);

        return ResponseEntity.ok(userMapper.toDto(updatedUserEntity));
    }
}
