package org.radon.teleeat.order.infrastructure.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.radon.teleeat.order.domain.OrderStatus;
import org.radon.teleeat.user.infrastructure.repository.entity.UserEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus = OrderStatus.CREATED;
    private String address;
    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<OrderItemEntity> items = new ArrayList<>();
    @Transient
    private BigDecimal total_price;
    private LocalDateTime created_at = LocalDateTime.now();
    private LocalDateTime updated_at = LocalDateTime.now();

    public OrderEntity(UserEntity user, OrderStatus orderStatus, String address, List<OrderItemEntity> items, LocalDateTime created_at, LocalDateTime updated_at) {
        this.user = user;
        this.orderStatus = orderStatus;
        this.address = address;
        this.items = items;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public OrderEntity(UserEntity user) {
        this.user = user;
    }

    public BigDecimal getTotalPrice() {
        return items.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getCount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
