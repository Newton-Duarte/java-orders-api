package com.newtonduarte.orders_api.services;

import com.newtonduarte.orders_api.domain.dto.CreateProductDto;
import com.newtonduarte.orders_api.domain.dto.UpdateProductDto;
import com.newtonduarte.orders_api.domain.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductService {
    Page<ProductEntity> findAll(Pageable pageable, String search);
    ProductEntity createProduct(CreateProductDto createProductDto);
    ProductEntity updateProduct(Long id, UpdateProductDto updateProductDto);
    Optional<ProductEntity> findOne(Long id);
    boolean isExists(Long id);
    ProductEntity partialUpdate(Long id, ProductEntity productEntity);
    void delete(Long id);
}
