package com.newtonduarte.orders_api.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpDto {
    @NotBlank(message = "Name is required!")
    @Size(min = 2, max = 50, message = "Name must be between {min} and {max} characters")
    private String name;

    @NotBlank(message = "Email is required!")
    @Email(message = "Invalid email!")
    private String email;

    @NotBlank(message = "Password is required!")
    @Size(min = 6, max = 50, message = "Password must be between {min} and {max} characters")
    private String password;
}
