package org.radon.teleeat.order.application.port.in;

import org.radon.teleeat.order.domain.OrderItem;

public interface AddOrderItemUseCase {
    void addOrderItem(OrderItem orderItem);
}
