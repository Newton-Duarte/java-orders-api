package com.newtonduarte.orders_api.domain;

import com.newtonduarte.orders_api.domain.dto.CreateOrderProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateOrderRequest {
    @Builder.Default
    private List<CreateOrderProductDto> products = new ArrayList<>();

    private OrderStatus status;

    private String comments;

    public Double getTotalOrderPrice() {
        double sum = 0D;
        List<CreateOrderProductDto> orderProducts = getProducts();
        for (CreateOrderProductDto op : orderProducts) {
            sum += op.getTotalPrice();
        }
        return sum;
    }
}
