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

    @PostMapping(path = "/sign-in")
    private ResponseEntity<AuthResponse> signIn(@RequestBody LoginRequest loginRequest) {
        UserDetails userDetails = authService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());

        String token = authService.generateToken(userDetails);
        AuthResponse authResponse = AuthResponse.builder()
                .token(token)
                .expiresIn(86400) // 24 h
                .build();

        return ResponseEntity.ok(authResponse);
    }
}
