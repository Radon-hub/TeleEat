package org.radon.teleeat.order.infrastructure.adapter.mapper;

import org.radon.teleeat.food.infrastructure.adapter.mapper.FoodMapper;
import org.radon.teleeat.order.domain.Order;
import org.radon.teleeat.order.domain.OrderItem;
import org.radon.teleeat.order.infrastructure.repository.entity.OrderEntity;
import org.radon.teleeat.order.infrastructure.repository.entity.OrderItemEntity;

public class OrderMappers {


    public static OrderEntity orderEntityFromUserId(Long userId){
        return new OrderEntity(userId);
    }

    public static Order fromOrderEntityToOrder(OrderEntity orderEntity) {
        return new Order.Builder()
                .id(orderEntity.getId())
                .userId(orderEntity.getUserId())
                .orderStatus(orderEntity.getOrderStatus())
                .address(orderEntity.getAddress())
                .items(orderEntity.getItems().stream().map(OrderMappers::fromOrderItemEntityToOrderItem).toList())
                .totalPrice(orderEntity.getTotalPrice())
                .created_at(orderEntity.getCreated_at())
                .updated_at(orderEntity.getUpdated_at())
                .build();
    }


    public static OrderEntity fromOrderToOrderEntity(Order order) {
        return new OrderEntity(
                order.getUserId(),
                order.getOrderStatus(),
                order.getAddress(),
                order.getItems().stream().map(OrderMappers::fromOrderItemToOrderItemEntity).toList(),
                order.getCreated_at(),
                order.getUpdated_at()
        );
    }

    public static OrderItemEntity fromOrderItemToOrderItemEntity(OrderItem orderItem) {
        return new OrderItemEntity(
                FoodMapper.fromFood(orderItem.getFood()),
                orderItem.getPrice()
        );
    }

    public static OrderItem fromOrderItemEntityToOrderItem(OrderItemEntity orderItemEntity) {
        return new OrderItem.Builder()
                .id(orderItemEntity.getId())
                .food(FoodMapper.fromFoodEntity(orderItemEntity.getFood()))
                .price(orderItemEntity.getPrice())
                .count(orderItemEntity.getCount())
                .created_at(orderItemEntity.getCreated_at())
                .updated_at(orderItemEntity.getUpdated_at())
                .build();
    }

}
