package com.newtonduarte.orders_api.services;

import com.newtonduarte.orders_api.domain.CreateOrderRequest;
import com.newtonduarte.orders_api.domain.UpdateOrderRequest;
import com.newtonduarte.orders_api.domain.entities.OrderEntity;

import java.util.List;

public interface OrderService {
    List<OrderEntity> getOrders();
    OrderEntity getOrder(Long id);
    OrderEntity createOrder(CreateOrderRequest createOrderRequest);
    OrderEntity updateOrder(Long id, UpdateOrderRequest updateOrderRequest);
    void deleteOrder(Long id);
}
