package org.radon.teleeat.order.application.service;

import org.radon.teleeat.order.application.port.in.AddOrderItemUseCase;
import org.radon.teleeat.order.application.port.in.RemoveOrderItemUseCase;
import org.radon.teleeat.order.application.port.out.OrderItemRepository;
import org.radon.teleeat.order.domain.OrderItem;
import org.springframework.stereotype.Service;

@Service
public class OrderItemService implements AddOrderItemUseCase, RemoveOrderItemUseCase {

    private final OrderItemRepository orderItemRepository;

    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }


    @Override
    public void addOrderItem(OrderItem orderItem) {
        orderItemRepository.addOrderItem(orderItem);
    }

    @Override
    public void removeOrderItem(OrderItem orderItem) {
        orderItemRepository.removeOrderItem(orderItem);
    }
}
