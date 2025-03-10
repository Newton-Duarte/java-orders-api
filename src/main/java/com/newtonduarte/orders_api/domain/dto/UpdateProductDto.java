package com.newtonduarte.orders_api.domain.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductDto {
    @NotNull(message = "Product ID is required")
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Product name must be between {min} and {max} characters")
    @Pattern(regexp = "^[\\w\\s-]+$", message = "Product name can only contain letters, numbers, spaces, and hyphens")
    private String name;

    @NotBlank(message = "Price is required")
    private BigDecimal price;
}
