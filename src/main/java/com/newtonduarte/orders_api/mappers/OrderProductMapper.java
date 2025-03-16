package com.newtonduarte.orders_api.mappers;

import com.newtonduarte.orders_api.domain.dto.CreateOrderProductDto;
import com.newtonduarte.orders_api.domain.dto.OrderProductDto;
import com.newtonduarte.orders_api.domain.entities.OrderProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderProductMapper {
    OrderProductDto toDto(OrderProductEntity orderProductEntity);
    OrderProductEntity toEntity(OrderProductDto orderProductDto);
    CreateOrderProductDto toCreateOrderProductDto(OrderProductDto orderProductDto);
}
