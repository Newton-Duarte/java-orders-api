package com.newtonduarte.orders_api.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface AuthService {
    UserDetails authenticate(String email, String password);
    String generateToken(UserDetails userDetails);
}
