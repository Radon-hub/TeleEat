package org.radon.teleeat.order.application.service;

import jakarta.transaction.Transactional;
import org.radon.teleeat.order.application.port.in.GetOrderUseCase;
import org.radon.teleeat.order.application.port.in.UpdateOrderAddressUseCase;
import org.radon.teleeat.order.application.port.in.UpdateOrderStatusUseCase;
import org.radon.teleeat.order.application.port.out.OrderRepository;
import org.radon.teleeat.order.domain.Order;
import org.radon.teleeat.order.domain.OrderStatus;
import org.springframework.stereotype.Service;

@Service
public class OrderService implements GetOrderUseCase, UpdateOrderStatusUseCase, UpdateOrderAddressUseCase {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order getOrder(Long id) {
        return orderRepository.getOrder(id);
    }

    @Transactional
    @Override
    public Order updateOrderStatus(Long orderId, OrderStatus orderStatus) {
        return orderRepository.updateOrderStatus(orderId, orderStatus);
    }

    @Transactional
    @Override
    public Order updateAddress(Long orderId, String address) {
        return orderRepository.updateAddress(orderId, address);
    }
}
