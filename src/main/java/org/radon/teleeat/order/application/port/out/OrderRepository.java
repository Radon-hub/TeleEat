package org.radon.teleeat.order.application.port.out;

import org.radon.teleeat.order.domain.Order;
import org.radon.teleeat.order.domain.OrderStatus;

public interface OrderRepository {
    Order getOrder(Long id);
    Order getOpenOrder(Long userId);
    Order create(Order order);
    Order updateOrderStatus(Long orderId, OrderStatus orderStatus);
    Order updateAddress(Long orderId, String address);
    void save(Order order);
}
