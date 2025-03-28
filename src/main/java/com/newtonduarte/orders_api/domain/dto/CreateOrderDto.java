package com.newtonduarte.orders_api.domain.dto;

import com.newtonduarte.orders_api.domain.OrderStatus;
import jakarta.persistence.Transient;
import jakarta.validation.Valid;
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
    @Builder.Default
    @Size(min = 1, message = "Minimum of {min} product")
    private List<@Valid CreateOrderProductDto> products = new ArrayList<>();

    @NotNull(message = "Status is required")
    private OrderStatus status;

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
