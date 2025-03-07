package com.newtonduarte.orders_api.services;

import com.newtonduarte.orders_api.domain.entities.ProductEntity;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductEntity> findAll();
    ProductEntity save(ProductEntity productEntity);
    Optional<ProductEntity> findOne(Long id);
    boolean isExists(Long id);
    ProductEntity partialUpdate(Long id, ProductEntity productEntity);
    void delete(Long id);
}
