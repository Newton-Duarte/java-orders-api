package com.newtonduarte.orders_api.security;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class JwtUtils {
    public final static long defaultExpiresIn = 86400;

    private static String encodePassword(String password) {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        return passwordEncoder.encode(password);
    }
}
