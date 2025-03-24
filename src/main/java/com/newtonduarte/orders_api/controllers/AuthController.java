package com.newtonduarte.orders_api.controllers;

import com.newtonduarte.orders_api.domain.AuthResponse;
import com.newtonduarte.orders_api.domain.SignInRequest;
import com.newtonduarte.orders_api.domain.SignUpRequest;
import com.newtonduarte.orders_api.domain.dto.ApiErrorResponse;
import com.newtonduarte.orders_api.domain.dto.SignInDto;
import com.newtonduarte.orders_api.domain.dto.SignUpDto;
import com.newtonduarte.orders_api.mappers.AuthMapper;
import com.newtonduarte.orders_api.services.AuthService;
import com.newtonduarte.orders_api.utils.AuthResponseUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@Tag(name = "Authentication", description = "Endpoints for Auth operations")
public class AuthController {
    private final AuthService authService;
    private final AuthMapper authMapper;

    @PostMapping(path = "/sign-in")
    @Operation(summary = "Authenticates user by email and password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User authenticate successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = {
                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            }),
            @ApiResponse(responseCode = "401", description = "Invalid credentials", content = {
                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(schema = @Schema(implementation = Void.class))
            })
    })
    public ResponseEntity<AuthResponse> signIn(@Valid @RequestBody SignInDto signInDto) {
        SignInRequest signInRequest = authMapper.toSignInRequest(signInDto);
        UserDetails userDetails = authService.signIn(signInRequest);

        String token = authService.generateToken(userDetails);
        AuthResponse authResponse = AuthResponseUtils.generateAuthResponse(token);

        return ResponseEntity.ok(authResponse);
    }

    @PostMapping(path = "/sign-up")
    @Operation(summary = "Create a user passing name, email, and password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = {
                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            }),
            @ApiResponse(responseCode = "409", description = "Email already exist", content = {
                    @Content(schema = @Schema(implementation = Void.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(schema = @Schema(implementation = Void.class))
            })
    })
    public ResponseEntity<AuthResponse> signUp(@Valid @RequestBody SignUpDto signUpDto) {
        SignUpRequest signUpRequest = authMapper.toSignUpRequest(signUpDto);
        UserDetails userDetails = authService.signUp(signUpRequest);

        String token = authService.generateToken(userDetails);
        AuthResponse authResponse = AuthResponseUtils.generateAuthResponse(token);

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }
}
