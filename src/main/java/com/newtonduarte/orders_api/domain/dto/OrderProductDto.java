package com.newtonduarte.orders_api.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderProductDto {
    private Long id;
    private OrderDto order;
    private ProductDto product;
    private Integer quantity;
    private Double price;
    private Double total;
}
