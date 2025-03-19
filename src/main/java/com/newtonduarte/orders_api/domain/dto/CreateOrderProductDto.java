package com.newtonduarte.orders_api.domain.dto;

import jakarta.persistence.Transient;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateOrderProductDto {
    @NotNull(message = "Product Id is required")
    private Long productId;

    @Min(value = 1, message = "Quantity must be 1 or greater")
    private Integer quantity;

    @DecimalMin(value = "0", inclusive = false, message = "Price must be greater than 0")
    private Double price;

    @Transient
    public Double getTotalPrice() {
        return getPrice() * getQuantity();
    }
}
