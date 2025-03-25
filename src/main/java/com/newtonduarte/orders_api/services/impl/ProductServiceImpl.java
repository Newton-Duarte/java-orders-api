package com.newtonduarte.orders_api.services.impl;

import com.newtonduarte.orders_api.domain.dto.CreateProductDto;
import com.newtonduarte.orders_api.domain.dto.UpdateProductDto;
import com.newtonduarte.orders_api.domain.entities.ProductEntity;
import com.newtonduarte.orders_api.repositories.ProductRepository;
import com.newtonduarte.orders_api.services.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public List<ProductEntity> findAll(String search) {
        if (search == null) {
            return StreamSupport.stream(
                    productRepository
                            .findAll()
                            .spliterator(),
                    false)
                    .collect(Collectors.toList());
        }

        return StreamSupport.stream(
                        productRepository
                                .findByNameContainingIgnoreCase(search)
                                .spliterator(),
                        false)
                .collect(Collectors.toList());
    }

    @Override
    public ProductEntity createProduct(CreateProductDto createProductDto) {
        ProductEntity product = new ProductEntity();
        product.setName(createProductDto.getName());
        product.setPrice(createProductDto.getPrice());

        return productRepository.save(product);
    }

    @Override
    public ProductEntity updateProduct(Long id, UpdateProductDto updateProductDto) {
        ProductEntity existingProduct = productRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id " + id));

        existingProduct.setName(updateProductDto.getName());
        existingProduct.setPrice(updateProductDto.getPrice());

        return productRepository.save(existingProduct);
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
