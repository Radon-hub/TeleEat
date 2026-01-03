package org.radon.teleeat.order.domain;

import org.radon.teleeat.common.aop.exceptionHandling.OrderItemNotFoundException;
import org.radon.teleeat.food.domain.Food;
import org.radon.teleeat.user.domain.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order {
    private Long id;
//    private Long userId;
    private User user;
    private OrderStatus orderStatus;
    private String address;
    private List<OrderItem> items = new ArrayList<>();
    private BigDecimal totalPrice;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;


    public Order(Builder builder) {
        this.id = builder.id;
//        this.userId = builder.userId;
        this.user = builder.user;
        this.orderStatus = builder.orderStatus;
        this.address = builder.address;
        this.items.addAll(builder.items);
        this.totalPrice = builder.totalPrice;
        this.created_at = builder.created_at;
        this.updated_at = builder.updated_at;
    }

    public static class Builder{
        private Long id;
//        private Long userId;
        private User user;
        private OrderStatus orderStatus;
        private String address;
        private List<OrderItem> items = new ArrayList<>();
        private BigDecimal totalPrice;
        private LocalDateTime created_at;
        private LocalDateTime updated_at;

        public Builder id(Long id){
            this.id = id;
            return this;
        }
//        public Builder userId(Long userId){
//            this.userId = userId;
//            return this;
//        }
        public Builder user(User user){
            this.user = user;
            return this;
        }
        public Builder orderStatus(OrderStatus orderStatus){
            this.orderStatus = orderStatus;
            return this;
        }
        public Builder address(String address){
            this.address = address;
            return this;
        }
        public Builder items(List<OrderItem> items){
            this.items.addAll(items);
            return this;
        }
        public Builder totalPrice(BigDecimal totalPrice){
            this.totalPrice = totalPrice;
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
        public Order build(){
            return new Order(this);
        }
    }

    public void addItem(Food food){
        OrderItem item = items.stream()
                .filter(i -> i.getFood().getId().equals(food.getId()))
                .findFirst()
                .orElseGet(() -> {
                   OrderItem newItem = OrderItem.create(food,this);
                   items.add(newItem);
                   return newItem;
                });

        item.increment();
    }

    public void removeItem(Long itemId) {

        OrderItem item = items.stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(OrderItemNotFoundException::new);

        if (item.getCount() > 1) {
            item.decrement();
        } else {
            items.remove(item);
        }
    }

//    public boolean belongsTo(Long userId) {
//        return this.userId.equals(userId);
//    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public String getAddress() {
        return address;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
}
