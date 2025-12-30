package org.radon.teleeat.order.infrastructure.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.radon.teleeat.food.infrastructure.repository.entity.FoodEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "order_items_table")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "food_id", nullable = false)
    private FoodEntity food;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;
    private BigDecimal price;
    private Byte count = 1;
    private LocalDateTime created_at = LocalDateTime.now();
    private LocalDateTime updated_at = LocalDateTime.now();

    public OrderItemEntity(FoodEntity food, OrderEntity order,BigDecimal price) {
        this.food = food;
        this.order = order;
        this.price = price;
    }

    public OrderItemEntity(FoodEntity food,BigDecimal price) {
        this.food = food;
        this.price = price;
    }
}
