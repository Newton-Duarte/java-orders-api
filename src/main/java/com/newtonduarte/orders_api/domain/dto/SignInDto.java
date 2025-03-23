package com.newtonduarte.orders_api.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignInDto {
    @NotBlank(message = "Email is required!")
    @Email(message = "Invalid email!")
    private String email;

    @NotBlank(message = "Password is required!")
    @Size(min = 6, max = 50, message = "Password must be between {min} and {max} characters")
    private String password;
}
