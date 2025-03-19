package com.newtonduarte.orders_api.controllers;

import com.newtonduarte.orders_api.domain.CreateOrderRequest;
import com.newtonduarte.orders_api.domain.dto.CreateOrderDto;
import com.newtonduarte.orders_api.domain.dto.OrderDto;
import com.newtonduarte.orders_api.domain.dto.UpdateOrderDto;
import com.newtonduarte.orders_api.domain.entities.OrderEntity;
import com.newtonduarte.orders_api.mappers.OrderMapper;
import com.newtonduarte.orders_api.services.OrderService;
import jakarta.validation.Valid;
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

    @GetMapping(path = "/{id}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable Long id) {
        OrderEntity orderEntity = orderService.getOrder(id);
        return ResponseEntity.ok(orderMapper.toDto(orderEntity));
    }

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody CreateOrderDto createOrderDto) {
        OrderEntity orderEntity = orderService.createOrder(orderMapper.toCreateOrderRequest(createOrderDto));
        return new ResponseEntity<>(orderMapper.toDto(orderEntity), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long id, @Valid @RequestBody UpdateOrderDto updateOrderDto) {
        OrderEntity updateOrder = orderService.updateOrder(id, orderMapper.toUpdateOrderRequest(updateOrderDto));
        return ResponseEntity.ok(orderMapper.toDto(updateOrder));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
