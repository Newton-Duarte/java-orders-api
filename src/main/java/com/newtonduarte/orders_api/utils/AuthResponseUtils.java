package com.newtonduarte.orders_api.utils;

import com.newtonduarte.orders_api.domain.AuthResponse;
import com.newtonduarte.orders_api.security.JwtUtils;

public class AuthResponseUtils {
    public static AuthResponse generateAuthResponse(String token) {
        return AuthResponse.builder()
                .token(token)
                .expiresIn(JwtUtils.defaultExpiresIn) // 24 h
                .build();
    }
}
