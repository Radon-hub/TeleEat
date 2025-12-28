package org.radon.teleeat.order.application.port.in;

import org.radon.teleeat.order.domain.Order;

public interface AddOrGetOrderUseCase {
    Order addOrGetOrder(Order order);
}
