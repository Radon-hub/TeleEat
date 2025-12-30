package org.radon.teleeat.order.application.port.in;

import org.radon.teleeat.order.domain.Order;
import org.radon.teleeat.order.domain.OrderStatus;

public interface UpdateOrderStatusUseCase {
    Order updateOrderStatus(Long orderId, OrderStatus orderStatus);
}
