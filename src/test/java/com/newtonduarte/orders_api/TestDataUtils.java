package com.newtonduarte.orders_api;

import com.newtonduarte.orders_api.domain.CreateOrderRequest;
import com.newtonduarte.orders_api.domain.OrderStatus;
import com.newtonduarte.orders_api.domain.UpdateOrderRequest;
import com.newtonduarte.orders_api.domain.dto.*;
import com.newtonduarte.orders_api.domain.entities.ProductEntity;
import com.newtonduarte.orders_api.domain.entities.UserEntity;

import java.util.List;

public final class TestDataUtils {
    private TestDataUtils() {}

    public static UserEntity createTestUserEntityA() {
        return UserEntity.builder()
                .name("John Doe")
                .email("johndoe@email.com")
                .password("123456")
                .build();
    }

    public static UserEntity createTestUserEntityB() {
        return UserEntity.builder()
                .name("Jane doe")
                .email("janedoe@email.com")
                .password("123456")
                .build();
    }

    public static UserEntity createTestUserEntityC() {
        return UserEntity.builder()
                .name("John Smith")
                .email("johnsmith@email.com")
                .password("123456")
                .build();
    }

    public static CreateUserDto createTestCreateUserDtoA() {
        return CreateUserDto.builder()
                .name("John Doe")
                .email("johndoe@email.com")
                .password("123456")
                .build();
    }

    public static UpdateUserDto createTestUpdateUserDtoA() {
        return UpdateUserDto.builder()
                .id(1L)
                .name("John Doe")
                .email("johndoe@email.com")
                .password("123456")
                .build();
    }

    public static ProductEntity createTestProductEntityA() {
        return ProductEntity.builder()
                .name("Product A")
                .price(10.00)
                .build();
    }

    public static ProductEntity createTestProductEntityB() {
        return ProductEntity.builder()
                .name("Product B")
                .price(10.00)
                .build();
    }

    public static ProductEntity createTestProductEntityC() {
        return ProductEntity.builder()
                .name("Product C")
                .price(10.00)
                .build();
    }

    public static CreateProductDto createTestCreateProductDtoA() {
        return CreateProductDto.builder()
                .name("Product A")
                .price(10.00)
                .build();
    }

    public static UpdateProductDto createTestUpdateProductDtoA() {
        return UpdateProductDto.builder()
                .id(1L)
                .name("Product A")
                .price(10.00)
                .build();
    }

    public static SignInDto createSignInDto(String email, String password) {
        return SignInDto.builder()
                .email(email)
                .password(password)
                .build();
    }

    public static CreateOrderDto createCreateOrderDto() {
        return CreateOrderDto.builder()
                .comments("")
                .status(OrderStatus.PENDING)
                .products(List.of(new CreateOrderProductDto[]{
                        CreateOrderProductDto.builder()
                                .productId(1L)
                                .quantity(1)
                                .price(2.00)
                                .build()
                }))
                .build();
    }

    public static CreateOrderRequest createCreateOrderRequest() {
        return CreateOrderRequest.builder()
                .comments("")
                .status(OrderStatus.PENDING)
                .products(List.of(new CreateOrderProductDto[]{
                        CreateOrderProductDto.builder()
                                .productId(1L)
                                .quantity(1)
                                .price(2.00)
                                .build()
                }))
                .build();
    }

    public static UpdateOrderRequest createUpdateOrderRequest() {
        return UpdateOrderRequest.builder()
                .id(1L)
                .comments("comments")
                .status(OrderStatus.PENDING)
                .products(List.of(new UpdateOrderProductDto[]{
                        UpdateOrderProductDto.builder()
                                .id(1L)
                                .orderId(1L)
                                .productId(1L)
                                .quantity(1)
                                .price(2.00)
                                .build()
                }))
                .build();
    }
}
