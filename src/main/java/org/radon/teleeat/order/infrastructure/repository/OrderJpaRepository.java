package org.radon.teleeat.order.infrastructure.repository;

import org.radon.teleeat.order.domain.OrderStatus;
import org.radon.teleeat.order.infrastructure.repository.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface OrderJpaRepository extends JpaRepository<OrderEntity,Long> {
    OrderEntity findOrderEntitiesByUserIdAndOrderStatusIn(Long userId, Collection<OrderStatus> orderStatuses);

    OrderEntity findOrderEntitiesById(Long id);

    OrderEntity findOrderEntitiesByUserId(Long userId);

    OrderEntity findOrderEntitiesByUserIdAndOrderStatus(Long userId, OrderStatus orderStatus);

    OrderEntity findOrderEntitiesByUserIdAndOrderStatusIn(Long userId, Collection<OrderStatus> orderStatuses);
}
