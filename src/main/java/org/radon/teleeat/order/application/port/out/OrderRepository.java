package org.radon.teleeat.order.application.port.out;

import org.radon.teleeat.order.domain.Order;

public interface OrderRepository {
    Order addOrGetOrder(Order order);
    void removeOrder(Order order);
    Order getOrder(Order order);
}
