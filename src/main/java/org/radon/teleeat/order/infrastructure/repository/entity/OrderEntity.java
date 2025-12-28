package org.radon.teleeat.order.infrastructure.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.radon.teleeat.order.domain.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Table(name = "orders_table")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus;
    private String address;
    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<OrderItemEntity> items;
    @Transient
    private BigDecimal total_price;
    private LocalDateTime created_at = LocalDateTime.now();
    private LocalDateTime updated_at = LocalDateTime.now();

    public OrderEntity(Long userId, OrderStatus orderStatus, String address, List<OrderItemEntity> items, LocalDateTime created_at, LocalDateTime updated_at) {
        this.userId = userId;
        this.orderStatus = orderStatus;
        this.address = address;
        this.items = items;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.total_price = items.stream()
                .map(item ->
                        item.getFood().getPrice()
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
