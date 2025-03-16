package com.newtonduarte.orders_api.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@Entity
@Table(name = "order_products")
@IdClass(OrderProductId.class)
public class OrderProductEntity {
    @Id
    private Long id;

    @Id
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, foreignKey = @ForeignKey(name = "fk_product_order_product"))
    private ProductEntity product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Double total;

    @Transient
    public Double getTotalPrice() {
        return getPrice() * getQuantity();
    }
}
