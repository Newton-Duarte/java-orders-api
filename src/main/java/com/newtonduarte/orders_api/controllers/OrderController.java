package com.newtonduarte.orders_api.controllers;

import com.newtonduarte.orders_api.config.SecurityConfig;
import com.newtonduarte.orders_api.domain.dto.ApiErrorResponse;
import com.newtonduarte.orders_api.domain.dto.CreateOrderDto;
import com.newtonduarte.orders_api.domain.dto.OrderDto;
import com.newtonduarte.orders_api.domain.dto.UpdateOrderDto;
import com.newtonduarte.orders_api.domain.entities.OrderEntity;
import com.newtonduarte.orders_api.mappers.OrderMapper;
import com.newtonduarte.orders_api.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/orders")
@SecurityRequirement(name = SecurityConfig.SECURITY)
@Tag(name = "Orders", description = "Endpoints for Order entity (requires auth)")
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @GetMapping
    @Operation(summary = "Get a list of orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request success"),
            @ApiResponse(responseCode = "403", description = "Forbidden request (Requires auth)", content = {
                    @Content(schema = @Schema(implementation = Void.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            })
    })
    public ResponseEntity<List<OrderDto>> getOrders() {
        List<OrderEntity> orders = orderService.getOrders();
        List<OrderDto> ordersDto = orders.stream().map(orderMapper::toDto).toList();
        return ResponseEntity.ok(ordersDto);
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Get a single order passing by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request success"),
            @ApiResponse(responseCode = "403", description = "Forbidden request (Requires auth)", content = {
                    @Content(schema = @Schema(implementation = Void.class))
            }),
            @ApiResponse(responseCode = "404", description = "Order not found", content = {
                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            })
    })
    public ResponseEntity<OrderDto> getOrder(@PathVariable Long id) {
        OrderEntity orderEntity = orderService.getOrder(id);
        return ResponseEntity.ok(orderMapper.toDto(orderEntity));
    }

    @PostMapping
    @Operation(summary = "Create a single order passing the required fields")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = {
                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            }),
            @ApiResponse(responseCode = "403", description = "Forbidden request (Requires auth)", content = {
                    @Content(schema = @Schema(implementation = Void.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            })
    })
    public ResponseEntity<OrderDto> createOrder(
            @Valid @RequestBody CreateOrderDto createOrderDto,
            @RequestAttribute Long userId
    ) {
        OrderEntity orderEntity = orderService.createOrder(userId, orderMapper.toCreateOrderRequest(createOrderDto));
        return new ResponseEntity<>(orderMapper.toDto(orderEntity), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    @Operation(summary = "Update a single order passing the order id and the required fields")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = {
                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            }),
            @ApiResponse(responseCode = "403", description = "Forbidden request (Requires auth)", content = {
                    @Content(schema = @Schema(implementation = Void.class))
            }),
            @ApiResponse(responseCode = "404", description = "Order not found", content = {
                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            })
    })
    public ResponseEntity<OrderDto> updateOrder(
            @PathVariable Long id,
            @Valid @RequestBody UpdateOrderDto updateOrderDto,
            @RequestAttribute Long userId
    ) {
        OrderEntity updateOrder = orderService.updateOrder(id, userId, orderMapper.toUpdateOrderRequest(updateOrderDto));
        return ResponseEntity.ok(orderMapper.toDto(updateOrder));
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete a single order passing the order id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Order deleted successfully", content = {
                    @Content(schema = @Schema(implementation = Void.class))
            }),
            @ApiResponse(responseCode = "403", description = "Forbidden request (Requires auth)", content = {
                    @Content(schema = @Schema(implementation = Void.class))
            }),
            @ApiResponse(responseCode = "404", description = "Order not found", content = {
                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            })
    })
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
