package org.radon.teleeat.order.application.port.out;

import org.radon.teleeat.order.domain.Order;

public interface OrderRepository {
    Order getOrder(Order order);
}
