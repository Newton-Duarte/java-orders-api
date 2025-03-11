package com.newtonduarte.orders_api;

import com.newtonduarte.orders_api.domain.dto.CreateProductDto;
import com.newtonduarte.orders_api.domain.dto.CreateUserDto;
import com.newtonduarte.orders_api.domain.dto.UpdateProductDto;
import com.newtonduarte.orders_api.domain.dto.UpdateUserDto;
import com.newtonduarte.orders_api.domain.entities.ProductEntity;
import com.newtonduarte.orders_api.domain.entities.UserEntity;

import java.math.BigDecimal;

public final class TestDataUtils {
    private TestDataUtils() {}

    public static UserEntity createTestUserEntityA() {
        return UserEntity.builder()
                .name("John Doe")
                .email("johndoe@email.com")
                .build();
    }

    public static UserEntity createTestUserEntityB() {
        return UserEntity.builder()
                .name("Jane doe")
                .email("janedoe@email.com")
                .build();
    }

    public static UserEntity createTestUserEntityC() {
        return UserEntity.builder()
                .name("John Smith")
                .email("johnsmith@email.com")
                .build();
    }

    public static CreateUserDto createTestCreateUserDtoA() {
        return CreateUserDto.builder()
                .name("John Doe")
                .email("johndoe@email.com")
                .build();
    }

    public static UpdateUserDto createTestUpdateUserDtoA() {
        return UpdateUserDto.builder()
                .id(1L)
                .name("John Doe")
                .email("johndoe@email.com")
                .build();
    }

    public static ProductEntity createTestProductEntityA() {
        return ProductEntity.builder()
                .name("Product A")
                .price(BigDecimal.valueOf(10.00))
                .build();
    }

    public static ProductEntity createTestProductEntityB() {
        return ProductEntity.builder()
                .name("Product B")
                .price(BigDecimal.valueOf(10.00))
                .build();
    }

    public static ProductEntity createTestProductEntityC() {
        return ProductEntity.builder()
                .name("Product C")
                .price(BigDecimal.valueOf(10.00))
                .build();
    }

    public static CreateProductDto createTestCreateProductDtoA() {
        return CreateProductDto.builder()
                .name("Product A")
                .price(BigDecimal.valueOf(10.00))
                .build();
    }

    public static UpdateProductDto createTestUpdateProductDtoA() {
        return UpdateProductDto.builder()
                .id(1L)
                .name("Product A")
                .price(BigDecimal.valueOf(10.00))
                .build();
    }
}
