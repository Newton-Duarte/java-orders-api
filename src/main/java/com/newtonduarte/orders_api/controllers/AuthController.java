package com.newtonduarte.orders_api.controllers;

import com.newtonduarte.orders_api.domain.AuthResponse;
import com.newtonduarte.orders_api.domain.LoginRequest;
import com.newtonduarte.orders_api.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
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
}
