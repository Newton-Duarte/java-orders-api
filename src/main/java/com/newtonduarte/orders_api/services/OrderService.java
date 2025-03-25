package com.newtonduarte.orders_api.services;

import com.newtonduarte.orders_api.domain.CreateOrderRequest;
import com.newtonduarte.orders_api.domain.UpdateOrderRequest;
import com.newtonduarte.orders_api.domain.entities.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    Page<OrderEntity> getOrders(Pageable pageable);
    OrderEntity getOrder(Long id);
    OrderEntity createOrder(Long userId, CreateOrderRequest createOrderRequest);
    OrderEntity updateOrder(Long postId, Long userId, UpdateOrderRequest updateOrderRequest);
    void deleteOrder(Long id);
}
