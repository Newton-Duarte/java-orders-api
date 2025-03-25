package com.newtonduarte.orders_api.repositories;

import com.newtonduarte.orders_api.domain.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByNameContainingIgnoreCase(String name);
}
