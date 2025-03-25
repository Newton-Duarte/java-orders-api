package com.newtonduarte.orders_api.repositories;

import com.newtonduarte.orders_api.domain.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long>, PagingAndSortingRepository<ProductEntity, Long> {
    Page<ProductEntity> findByNameContainingIgnoreCase(Pageable pageable, String name);
}
