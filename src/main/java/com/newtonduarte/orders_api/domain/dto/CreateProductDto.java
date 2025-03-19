package com.newtonduarte.orders_api.domain.dto;

import jakarta.validation.constraints.*;
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

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be greater than or equals to 0")
    private Double price;
}
