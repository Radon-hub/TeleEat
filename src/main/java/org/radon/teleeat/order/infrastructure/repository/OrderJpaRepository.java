package org.radon.teleeat.order.infrastructure.repository;

import org.radon.teleeat.order.domain.OrderStatus;
import org.radon.teleeat.order.infrastructure.repository.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<OrderEntity,Long> {
    OrderEntity findOrderEntityByUserIdAndOrderStatus(Long userId, OrderStatus orderStatus);

    OrderEntity findOrderEntitiesById(Long id);
}
