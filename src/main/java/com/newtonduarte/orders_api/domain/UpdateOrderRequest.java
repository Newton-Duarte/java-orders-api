package com.newtonduarte.orders_api.domain;

import com.newtonduarte.orders_api.domain.dto.UpdateOrderProductDto;
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
public class UpdateOrderRequest {
    private Long id;

    private Long userId;

    @Builder.Default
    private List<UpdateOrderProductDto> products = new ArrayList<>();

    private OrderStatus status;

    private String comments;

    public Double getTotalOrderPrice() {
        double sum = 0D;
        List<UpdateOrderProductDto> orderProducts = getProducts();
        for (UpdateOrderProductDto op : orderProducts) {
            sum += op.getTotalPrice();
        }
        return sum;
    }
}
