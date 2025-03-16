package com.newtonduarte.orders_api.domain.dto;

import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderDto {
    @NotNull(message = "User Id is required")
    private Long userId;

    @Builder.Default
    @Size(min = 1, message = "Minimum of {min} product")
    private List<CreateOrderProductDto> products = new ArrayList<>();

    private String comments;

    @Transient
    public Double getTotalOrderPrice() {
        double sum = 0D;
        List<CreateOrderProductDto> orderProducts = getProducts();
        for (CreateOrderProductDto op : orderProducts) {
            sum += op.getTotalPrice();
        }
        return sum;
    }
}
