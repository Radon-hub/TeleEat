package org.radon.teleeat.order.presentation.mapper;


import org.radon.teleeat.common.dto.PagedResponse;
import org.radon.teleeat.food.domain.Food;
import org.radon.teleeat.food.infrastructure.adapter.mapper.FoodMapper;
import org.radon.teleeat.food.presentation.dto.FoodResponse;
import org.radon.teleeat.food.presentation.dto.mapper.FoodDtoMapper;
import org.radon.teleeat.order.domain.Order;
import org.radon.teleeat.order.domain.OrderItem;
import org.radon.teleeat.order.presentation.dto.AddOrderItemRequest;
import org.radon.teleeat.order.presentation.dto.OrderItemResponse;
import org.radon.teleeat.order.presentation.dto.OrderResponse;
import org.radon.teleeat.order.presentation.dto.RemoveOrderItemRequest;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public class OrderDtoMapper {


    public static OrderItem fromIdToRemoveOrderItemRequest(RemoveOrderItemRequest removeOrderItemRequest) {
        return new OrderItem.Builder().id(removeOrderItemRequest.getOrderItemId()).order(new Order.Builder().userId(removeOrderItemRequest.getUserId()).build()).build();
    }

    public static OrderItem fromAddOrderItemRequest(AddOrderItemRequest addOrderItemRequest) {
        return new OrderItem.Builder()
                .order(new Order.Builder().userId(addOrderItemRequest.getUserId()).build())
                .food(new Food.Builder().id(addOrderItemRequest.getFoodId()).build())
                .build();
    }

    public static PagedResponse<OrderResponse> toPagedOrderResponse(Page<Order> orders) {
        return new PagedResponse<>(
                orders.getContent().stream().map(OrderDtoMapper::fromOrder).toList(),
                orders.getNumber(),
                orders.getSize(),
                orders.getTotalPages(),
                orders.getTotalElements()
        );
    }


    public static Order fromOrderIdRequest(Long orderId) {
        return new Order.Builder().id(orderId).build();
    }

    public static OrderItemResponse fromOrderItem(OrderItem orderItem) {
        return new OrderItemResponse(
                orderItem.getId(),
                orderItem.getFood().getName(),
                orderItem.getFood().getPrice(),
                orderItem.getCount()
        );
    }

    public static OrderResponse fromOrder(Order order) {

        return new OrderResponse(
                order.getId(),
                order.getOrderStatus(),
                order.getAddress(),
                order.getItems().stream().map(OrderDtoMapper::fromOrderItem).toList(),
                order.getTotalPrice()
        );
    }

}
