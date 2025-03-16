package com.newtonduarte.orders_api.services.impl;

import com.newtonduarte.orders_api.domain.dto.CreateOrderDto;
import com.newtonduarte.orders_api.domain.dto.CreateOrderProductDto;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
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
    @Transactional
    public OrderEntity createOrder(CreateOrderDto createOrderDto) {
        OrderEntity createOrder = new OrderEntity();
        List<OrderProductEntity> createOrderProducts = new ArrayList<>();

        Long userId = createOrderDto.getUserId();
        Optional<UserEntity> foundUser = userService.findOne(userId);

        if (foundUser.isEmpty()) {
            throw new EntityNotFoundException("User not found with id " + userId);
        }

        UserEntity user = foundUser.get();
        createOrder.setUser(user);

        List<CreateOrderProductDto> createOrderProductsDto = createOrderDto.getProducts();
        createOrder.setTotal(createOrderDto.getTotalOrderPrice());

        OrderEntity savedOrder = orderRepository.save(createOrder);

        long orderProductIndex = 1L;
        for (var orderProductDto : createOrderProductsDto) {
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
        savedOrder.setComments(createOrderDto.getComments());

        return orderRepository.save(savedOrder);
    }
}
