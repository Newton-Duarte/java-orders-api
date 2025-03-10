package com.newtonduarte.orders_api.mappers;

import com.newtonduarte.orders_api.domain.dto.*;
import com.newtonduarte.orders_api.domain.entities.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {
    ProductDto toDto(ProductEntity product);
    ProductEntity toEntity(ProductDto productDto);
    CreateProductDto toCreateProductDto(CreateProductDto createProductDto);
    UpdateProductDto toUpdateProductDto(UpdateProductDto updateProductDto);
}
