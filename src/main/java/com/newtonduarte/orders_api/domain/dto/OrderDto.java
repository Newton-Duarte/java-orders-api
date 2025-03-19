package com.newtonduarte.orders_api.domain.dto;

import com.newtonduarte.orders_api.domain.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private Long id;
    private String comments;
    private OrderStatus status;
    private UserDto user;
    private List<OrderProductDto> products = new ArrayList<>();
    private Double total;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
