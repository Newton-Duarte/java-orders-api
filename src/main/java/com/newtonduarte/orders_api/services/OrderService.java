package com.newtonduarte.orders_api.services;

import com.newtonduarte.orders_api.domain.dto.CreateOrderDto;
import com.newtonduarte.orders_api.domain.entities.OrderEntity;

import java.util.List;

public interface OrderService {
    List<OrderEntity> getOrders();
    OrderEntity createOrder(CreateOrderDto createOrderDto);
}
