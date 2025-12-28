package org.radon.teleeat.order.presentation.mapper;


import org.radon.teleeat.food.domain.Food;
import org.radon.teleeat.food.infrastructure.adapter.mapper.FoodMapper;
import org.radon.teleeat.order.domain.Order;
import org.radon.teleeat.order.domain.OrderItem;
import org.radon.teleeat.order.presentation.dto.AddOrderItemRequest;
import org.radon.teleeat.order.presentation.dto.AddOrderRequest;
import org.radon.teleeat.order.presentation.dto.OrderItemResponse;
import org.radon.teleeat.order.presentation.dto.OrderResponse;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public class OrderDtoMapper {

    public static Order fromAddOrderRequest(AddOrderRequest addOrderRequest) {
        return new Order.Builder()
                .userId(addOrderRequest.getUserId())
                .build();
    }

    public static OrderItem fromAddOrderItemRequest(AddOrderItemRequest addOrderItemRequest) {
        return new OrderItem.Builder()
                .order(new Order.Builder().userId(addOrderItemRequest.getUserId()).build())
                .food(new Food.Builder().id(addOrderItemRequest.getFoodId()).build())
                .build();
    }

    public static Order fromOrderIdRequest(Long orderId) {
        return new Order.Builder().id(orderId).build();
    }

    public static OrderItemResponse fromOrderItem(OrderItem orderItem) {
        return new OrderItemResponse(
                orderItem.getOrder().getId(),
                orderItem.getFood().getName(),
                orderItem.getFood().getPrice(),
                Byte.valueOf("1")
        );
    }

    public static OrderResponse fromOrder(Order order) {

        Map<String, OrderItemResponse> groupedItems = new LinkedHashMap<>();

        for(OrderItem item : order.getItems()) {
            groupedItems.merge(
                    item.getFood().getId().toString(),
                    OrderDtoMapper.fromOrderItem(item),
                    (existing, incoming) -> {
                        existing.setCount(
                                (byte) (existing.getCount() + incoming.getCount())
                        );
                        return existing;
                    }
            );
        }


        BigDecimal totalPrice = groupedItems.values().stream()
                .map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getCount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        return new OrderResponse(
                order.getId(),
                order.getOrderStatus(),
                order.getAddress(),
                groupedItems.values().stream().toList(),
                totalPrice
        );
    }

}
