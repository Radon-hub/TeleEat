package org.radon.teleeat.order.application.port.in;

import org.radon.teleeat.order.domain.Order;

public interface GetOrderUseCase {
    Order getOrder(Order order);
}
