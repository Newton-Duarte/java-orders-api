package com.newtonduarte.orders_api.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductDto {
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Product name must be between {min} and {max} characters")
    @Pattern(regexp = "^[\\w\\s-]+$", message = "Product name can only contain letters, numbers, spaces, and hyphens")
    private String name;

    @NotBlank(message = "Price is required")
    private Double price;
}
