package com.newtonduarte.orders_api.repositories;

import com.newtonduarte.orders_api.domain.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long>, PagingAndSortingRepository<OrderEntity, Long> {
}
