package com.newtonduarte.orders_api.services;

import com.newtonduarte.orders_api.domain.dto.CreateProductDto;
import com.newtonduarte.orders_api.domain.dto.UpdateProductDto;
import com.newtonduarte.orders_api.domain.entities.ProductEntity;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductEntity> findAll();
    ProductEntity createProduct(CreateProductDto createProductDto);
    ProductEntity updateProduct(Long id, UpdateProductDto updateProductDto);
    Optional<ProductEntity> findOne(Long id);
    boolean isExists(Long id);
    ProductEntity partialUpdate(Long id, ProductEntity productEntity);
    void delete(Long id);
}
