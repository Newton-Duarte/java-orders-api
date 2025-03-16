package com.newtonduarte.orders_api.controllers;

import com.newtonduarte.orders_api.domain.dto.CreateOrderDto;
import com.newtonduarte.orders_api.domain.dto.OrderDto;
import com.newtonduarte.orders_api.domain.entities.OrderEntity;
import com.newtonduarte.orders_api.mappers.OrderMapper;
import com.newtonduarte.orders_api.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @GetMapping
    public ResponseEntity<List<OrderDto>> getOrders() {
        List<OrderEntity> orders = orderService.getOrders();
        List<OrderDto> ordersDto = orders.stream().map(orderMapper::toDto).toList();
        return ResponseEntity.ok(ordersDto);
    }

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody CreateOrderDto createOrderDto) {
        OrderEntity createdOrder = orderService.createOrder(createOrderDto);
        return new ResponseEntity<>(orderMapper.toDto(createdOrder), HttpStatus.CREATED);
    }
}
