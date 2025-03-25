package com.newtonduarte.orders_api.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserProfileDto {
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "User name must be between {min} and {max} characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    private String email;

    @Size(min = 6, max = 50, message = "Password must be between {min} and {max} characters")
    private String password;
}
