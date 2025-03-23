package com.newtonduarte.orders_api.services;

import com.newtonduarte.orders_api.domain.SignInRequest;
import com.newtonduarte.orders_api.domain.SignUpRequest;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthService {
    UserDetails signIn(SignInRequest signInRequest);
    UserDetails signUp(SignUpRequest signUpRequest);
    String generateToken(UserDetails userDetails);
    UserDetails validateToken(String token);
}
