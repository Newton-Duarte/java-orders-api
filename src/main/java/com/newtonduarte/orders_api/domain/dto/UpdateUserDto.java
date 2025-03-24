package com.newtonduarte.orders_api.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDto {
    @NotNull(message = "User ID is required")
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "User name must be between {min} and {max} characters")
    private String name;

    @NotBlank(message = "Email is required")
    private String email;

    @Size(min = 6, max = 50, message = "Password must be between {min} and {max} characters")
    private String password;
}
