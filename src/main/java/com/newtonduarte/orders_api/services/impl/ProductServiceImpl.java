package com.newtonduarte.orders_api.services.impl;

import com.newtonduarte.orders_api.domain.entities.ProductEntity;
import com.newtonduarte.orders_api.repositories.ProductRepository;
import com.newtonduarte.orders_api.services.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductEntity> findAll() {
        return StreamSupport.stream(productRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public ProductEntity save(ProductEntity productEntity) {
        return this.productRepository.save(productEntity);
    }

    @Override
    public Optional<ProductEntity> findOne(Long id) {
        return this.productRepository.findById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return this.productRepository.existsById(id);
    }

    @Override
    public ProductEntity partialUpdate(Long id, ProductEntity productEntity) {
        productEntity.setId(id);

        return productRepository.findById(id).map(existingAuthor -> {
            Optional.ofNullable(productEntity.getName()).ifPresent(existingAuthor::setName);
            Optional.ofNullable(productEntity.getPrice()).ifPresent(existingAuthor::setPrice);
            return productRepository.save(existingAuthor);
        }).orElseThrow(() -> new RuntimeException("Product does not exist"));
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
