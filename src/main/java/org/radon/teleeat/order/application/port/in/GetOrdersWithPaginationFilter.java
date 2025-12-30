package org.radon.teleeat.order.application.port.in;

import org.radon.teleeat.order.domain.Order;
import org.radon.teleeat.order.domain.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public interface GetOrdersWithPaginationFilter {
    Page<Order> getOrderWithFilter(String address, BigDecimal priceTo, Set<OrderStatus> statuses, LocalDateTime from , LocalDateTime to, Pageable pageable);
}
