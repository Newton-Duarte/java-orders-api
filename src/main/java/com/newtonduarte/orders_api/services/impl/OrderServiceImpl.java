package com.newtonduarte.orders_api.services.impl;

import com.newtonduarte.orders_api.domain.CreateOrderRequest;
import com.newtonduarte.orders_api.domain.OrderStatus;
import com.newtonduarte.orders_api.domain.dto.CreateOrderDto;
import com.newtonduarte.orders_api.domain.dto.CreateOrderProductDto;
import com.newtonduarte.orders_api.domain.dto.UpdateOrderDto;
import com.newtonduarte.orders_api.domain.dto.UpdateOrderProductDto;
import com.newtonduarte.orders_api.domain.entities.OrderEntity;
import com.newtonduarte.orders_api.domain.entities.OrderProductEntity;
import com.newtonduarte.orders_api.domain.entities.ProductEntity;
import com.newtonduarte.orders_api.domain.entities.UserEntity;
import com.newtonduarte.orders_api.mappers.OrderProductMapper;
import com.newtonduarte.orders_api.mappers.UserMapper;
import com.newtonduarte.orders_api.repositories.OrderRepository;
import com.newtonduarte.orders_api.services.OrderService;
import com.newtonduarte.orders_api.services.ProductService;
import com.newtonduarte.orders_api.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserMapper userMapper;
    private final OrderProductMapper orderProductMapper;
    private final UserService userService;
    private final ProductService productService;

    @Override
    public List<OrderEntity> getOrders() {
        return orderRepository.findAll();
    }

    @Override
    public OrderEntity getOrder(Long id) {
        return orderRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id " + id));
    }

    @Override
    @Transactional
    public OrderEntity createOrder(CreateOrderDto createOrderDto) {
        OrderEntity createOrder = new OrderEntity();
    public OrderEntity createOrder(CreateOrderRequest createOrderRequest) {
        OrderEntity orderEntity = new OrderEntity();
        List<OrderProductEntity> createOrderProducts = new ArrayList<>();

        Long userId = createOrderRequest.getUserId();
        Optional<UserEntity> foundUser = userService.findOne(userId);

        if (foundUser.isEmpty()) {
            throw new EntityNotFoundException("User not found with id " + userId);
        }

        UserEntity user = foundUser.get();
        orderEntity.setUser(user);

        List<CreateOrderProductDto> createOrderEntityProducts = createOrderRequest.getProducts();
        orderEntity.setTotal(createOrderRequest.getTotalOrderPrice());
        orderEntity.setStatus(createOrderRequest.getStatus());

        OrderEntity savedOrder = orderRepository.save(orderEntity);

        long orderProductIndex = 1L;
        for (var orderProductDto : createOrderEntityProducts) {
            Optional<ProductEntity> foundProduct = productService.findOne(orderProductDto.getProductId());

            if (foundProduct.isEmpty()) {
                throw new EntityNotFoundException("Product not found with id " + orderProductDto.getProductId());
            }

            ProductEntity product = foundProduct.get();

            createOrderProducts.add(
                    OrderProductEntity.builder()
                            .id(orderProductIndex)
                            .orderId(savedOrder.getId())
                            .product(product)
                            .quantity(orderProductDto.getQuantity())
                            .price(orderProductDto.getPrice())
                            .total(orderProductDto.getTotalPrice())
                            .build());

            orderProductIndex++;
        }

        savedOrder.setProducts(createOrderProducts);
        savedOrder.setComments(orderEntity.getComments());

        return orderRepository.save(savedOrder);
    }

    @Override
    @Transactional
    public OrderEntity updateOrder(Long id, UpdateOrderDto updateOrderDto) {
        OrderEntity existingOrder = orderRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id " + id));

        if (existingOrder.getStatus().equals(OrderStatus.COMPLETE)) {
            throw new IllegalArgumentException("Order status is complete and cannot be updated");
        }

        Long userId = updateOrderDto.getUserId();

        UserEntity existingUser = userService
                .findOne(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + id));

        List<UpdateOrderProductDto> updateOrderProductsDto = updateOrderDto.getProducts();
        List<OrderProductEntity> updateOrderProducts = new ArrayList<>();

        long orderProductIndex = 1L;
        for (var orderProductDto : updateOrderProductsDto) {
            ProductEntity product = productService
                    .findOne(orderProductDto.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException("Product not found with id " + id));

            updateOrderProducts.add(
                    OrderProductEntity.builder()
                            .id(orderProductDto.getId() > 0 ? orderProductDto.getId() : orderProductIndex)
                            .orderId(existingOrder.getId())
                            .product(product)
                            .quantity(orderProductDto.getQuantity())
                            .price(orderProductDto.getPrice())
                            .total(orderProductDto.getTotalPrice())
                            .build());

            orderProductIndex++;
        }

        existingOrder.setUser(existingUser);
        existingOrder.getProducts().clear();
        existingOrder.setStatus(updateOrderDto.getStatus());
        OrderEntity savedExistingOrder = orderRepository.save(existingOrder);
        savedExistingOrder.setProducts(updateOrderProducts);
        savedExistingOrder.setTotal(updateOrderDto.getTotalOrderPrice());
        savedExistingOrder.setComments(updateOrderDto.getComments());

        return orderRepository.save(savedExistingOrder);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
