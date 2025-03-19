package com.newtonduarte.orders_api.mappers;

import com.newtonduarte.orders_api.domain.CreateOrderRequest;
import com.newtonduarte.orders_api.domain.UpdateOrderRequest;
import com.newtonduarte.orders_api.domain.dto.*;
import com.newtonduarte.orders_api.domain.entities.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {
    OrderDto toDto(OrderEntity product);
    OrderEntity toEntity(OrderDto productDto);
    CreateOrderRequest toCreateOrderRequest(CreateOrderDto createOrderDto);
    UpdateOrderRequest toUpdateOrderRequest(UpdateOrderDto createOrderDto);
}
