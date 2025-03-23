package com.newtonduarte.orders_api.controllers;

import com.newtonduarte.orders_api.config.SecurityConfig;
import com.newtonduarte.orders_api.domain.dto.CreateProductDto;
import com.newtonduarte.orders_api.domain.dto.ProductDto;
import com.newtonduarte.orders_api.domain.dto.UpdateProductDto;
import com.newtonduarte.orders_api.domain.entities.ProductEntity;
import com.newtonduarte.orders_api.mappers.ProductMapper;
import com.newtonduarte.orders_api.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/products")
@RequiredArgsConstructor
@SecurityRequirement(name = SecurityConfig.SECURITY)
@Tag(name = "Products", description = "Endpoints for Product entity (requires auth)")
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping
    @Operation(summary = "Get a list of products")
    @ApiResponse(responseCode = "200", description = "Request success")
    @ApiResponse(responseCode = "403", description = "Forbidden request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<List<ProductDto>> getProducts() {
        List<ProductEntity> products = productService.findAll();
        List<ProductDto> productsDto = products.stream().map(productMapper::toDto).toList();
        return ResponseEntity.ok(productsDto);
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Get a single product passing by ID")
    @ApiResponse(responseCode = "200", description = "Request success")
    @ApiResponse(responseCode = "403", description = "Forbidden request")
    @ApiResponse(responseCode = "404", description = "Product not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id) {
        Optional<ProductEntity> foundProduct = productService.findOne(id);

        return foundProduct.map(productEntity -> {
            ProductDto productDto = productMapper.toDto(productEntity);
            return new ResponseEntity<>(productDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @Operation(summary = "Create a single product passing the required fields")
    @ApiResponse(responseCode = "201", description = "Product created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request body")
    @ApiResponse(responseCode = "403", description = "Forbidden request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody CreateProductDto createProductDto) {
        ProductEntity savedProductEntity = productService.createProduct(createProductDto);
        ProductDto productDto = productMapper.toDto(savedProductEntity);

        return new ResponseEntity<>(productDto, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    @Operation(summary = "Update a single product passing the product id and the required fields")
    @ApiResponse(responseCode = "200", description = "Product updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request body")
    @ApiResponse(responseCode = "403", description = "Forbidden request")
    @ApiResponse(responseCode = "404", description = "Product not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @Valid @RequestBody UpdateProductDto updateProductDto) {
        if (!productService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ProductEntity savedProductEntity = productService.updateProduct(id, updateProductDto);

        return new ResponseEntity<>(productMapper.toDto(savedProductEntity), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete a single product passing the product id")
    @ApiResponse(responseCode = "204", description = "Product deleted successfully")
    @ApiResponse(responseCode = "403", description = "Forbidden request")
    @ApiResponse(responseCode = "404", description = "Product not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        if (!productService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        productService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
