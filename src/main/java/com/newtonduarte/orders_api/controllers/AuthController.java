package com.newtonduarte.orders_api.controllers;

import com.newtonduarte.orders_api.config.SecurityConfig;
import com.newtonduarte.orders_api.domain.AuthResponse;
import com.newtonduarte.orders_api.domain.SignInRequest;
import com.newtonduarte.orders_api.domain.SignUpRequest;
import com.newtonduarte.orders_api.domain.dto.SignInDto;
import com.newtonduarte.orders_api.domain.dto.SignUpDto;
import com.newtonduarte.orders_api.mappers.AuthMapper;
import com.newtonduarte.orders_api.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
@SecurityRequirement(name = SecurityConfig.SECURITY)
@Tag(name = "Authentication", description = "Controller for Auth operations")
public class AuthController {
    private final AuthService authService;
    private final AuthMapper authMapper;

    @PostMapping(path = "/sign-in")
    @Operation(summary = "Authenticates user by email and password")
    @ApiResponse(responseCode = "200", description = "User authenticate successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request body")
    @ApiResponse(responseCode = "401", description = "Invalid credentials")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<AuthResponse> signIn(@Valid @RequestBody SignInDto signInDto) {
        SignInRequest signInRequest = authMapper.toSignInRequest(signInDto);
        UserDetails userDetails = authService.signIn(signInRequest);

        String token = authService.generateToken(userDetails);
        AuthResponse authResponse = AuthResponse.builder()
                .token(token)
                .expiresIn(86400) // 24 h
                .build();

        return ResponseEntity.ok(authResponse);
    }

    @PostMapping(path = "/sign-up")
    @Operation(summary = "Create a user passing name, email, and password")
    @ApiResponse(responseCode = "201", description = "User created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request body")
    @ApiResponse(responseCode = "409", description = "Email already exist")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<AuthResponse> signUp(@Valid @RequestBody SignUpDto signUpDto) {
        SignUpRequest signUpRequest = authMapper.toSignUpRequest(signUpDto);
        UserDetails userDetails = authService.signUp(signUpRequest);

        String token = authService.generateToken(userDetails);
        AuthResponse authResponse = AuthResponse.builder()
                .token(token)
                .expiresIn(86400) // 24 h
                .build();

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }
}
