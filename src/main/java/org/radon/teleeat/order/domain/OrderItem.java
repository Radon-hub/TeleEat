package org.radon.teleeat.order.domain;

import org.radon.teleeat.food.domain.Food;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderItem {
    private Long id;
    private Food food;
    private Order order;
    private BigDecimal price;
    private Byte count;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;


    public OrderItem(Builder builder) {
        this.id = builder.id;
        this.food = builder.food;
        this.order = builder.order;
        this.price = builder.price;
        this.count = builder.count;
        this.created_at = builder.created_at;
        this.updated_at = builder.updated_at;
    }

    public static class Builder {
        private Long id;
        private Food food;
        private Order order;
        private BigDecimal price;
        private Byte count;
        private LocalDateTime created_at;
        private LocalDateTime updated_at;

        public Builder id(Long id){
            this.id = id;
            return this;
        }

        public Builder food(Food food){
            this.food = food;
            return this;
        }
        public Builder order(Order order){
            this.order = order;
            return this;
        }
        public Builder created_at(LocalDateTime created_at){
            this.created_at = created_at;
            return this;
        }
        public Builder updated_at(LocalDateTime updated_at){
            this.updated_at = updated_at;
            return this;
        }
        public Builder price(BigDecimal price){
            this.price = price;
            return this;
        }
        public Builder count(Byte count){
            this.count = count;
            return this;
        }
        public OrderItem build(){
            return new OrderItem(this);
        }
    }

    public Long getId() {
        return id;
    }

    public Food getFood() {
        return food;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Byte getCount() {
        return count;
    }

    public Order getOrder() {
        return order;
    }
}
