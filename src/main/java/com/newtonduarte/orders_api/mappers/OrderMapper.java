package com.newtonduarte.orders_api.mappers;

import com.newtonduarte.orders_api.domain.dto.*;
import com.newtonduarte.orders_api.domain.entities.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {
    OrderDto toDto(OrderEntity product);
    OrderEntity toEntity(OrderDto productDto);
    CreateOrderDto toCreateOrderDto(CreateOrderDto createOrderDto);
}
