package org.radon.teleeat.order.application.port.out;

import org.radon.teleeat.order.domain.OrderItem;

public interface OrderItemRepository {
    void addOrderItem(OrderItem orderItem);
    void removeOrderItem(OrderItem orderItem);
}
