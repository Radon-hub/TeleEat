package org.radon.teleeat.order.infrastructure.repository;

import org.radon.teleeat.order.infrastructure.repository.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemsJpaRepository extends JpaRepository<OrderItemEntity,Long> {
}
