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
public class UpdateOrderProductDto {
    @NotNull(message = "Id is required")
    private Long id;

    @NotNull(message = "Order Id is required")
    private Long orderId;

    @NotNull(message = "Product Id is required")
    private Long productId;

    @Min(1)
    private Integer quantity;

    @DecimalMin(value = "0.0")
    private Double price;

    @Transient
    public Double getTotalPrice() {
        return getPrice() * getQuantity();
    }
}
