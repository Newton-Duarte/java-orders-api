package com.newtonduarte.orders_api;

import com.newtonduarte.orders_api.domain.entities.ProductEntity;
import com.newtonduarte.orders_api.domain.entities.UserEntity;

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
                .name("Jane Doe")
                .email("janedoe@email.com")
                .build();
    }

    public static UserEntity createTestUserEntityC() {
        return UserEntity.builder()
                .name("John Smith")
                .email("johnsmith@email.com")
                .build();
    }

    public static ProductEntity createTestProductEntityA() {
        return ProductEntity.builder()
                .name("Product A")
                .price(10.0)
                .build();
    }

    public static ProductEntity createTestProductEntityB() {
        return ProductEntity.builder()
                .name("Product B")
                .price(15.0)
                .build();
    }

    public static ProductEntity createTestProductEntityC() {
        return ProductEntity.builder()
                .name("Product C")
                .price(20.0)
                .build();
    }
}
