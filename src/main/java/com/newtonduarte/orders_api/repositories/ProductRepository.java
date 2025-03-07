package com.newtonduarte.orders_api.repositories;

import com.newtonduarte.orders_api.domain.entities.ProductEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<ProductEntity, Long> {
}
