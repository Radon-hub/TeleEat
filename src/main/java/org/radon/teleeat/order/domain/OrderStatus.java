package org.radon.teleeat.order.domain;

public enum OrderStatus {
    CREATED,
    PENDING_PAYMENT,
    PROCESSING,
    COMPLETED,
    CANCELLED,
    DELIVERY
}
