package org.radon.teleeat.order.application.service;

import org.radon.teleeat.order.application.port.in.AddOrGetOrderUseCase;
import org.radon.teleeat.order.application.port.in.GetOrderUseCase;
import org.radon.teleeat.order.application.port.in.RemoveOrderUseCase;
import org.radon.teleeat.order.application.port.out.OrderRepository;
import org.radon.teleeat.order.domain.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderService implements GetOrderUseCase, AddOrGetOrderUseCase, RemoveOrderUseCase {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order getOrder(Order order) {
        return orderRepository.getOrder(order);
    }

    @Override
    public void removeOrder(Order order) {
        orderRepository.removeOrder(order);
    }

    @Override
    public Order addOrGetOrder(Order order) {
        return orderRepository.addOrGetOrder(order);
    }
}
