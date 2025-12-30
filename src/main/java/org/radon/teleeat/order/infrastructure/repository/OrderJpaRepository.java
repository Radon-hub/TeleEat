package org.radon.teleeat.order.infrastructure.repository;

import org.radon.teleeat.order.domain.OrderStatus;
import org.radon.teleeat.order.infrastructure.repository.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderJpaRepository extends JpaRepository<OrderEntity,Long>, JpaSpecificationExecutor<OrderEntity> {
    OrderEntity findOrderEntityByUserIdAndOrderStatus(Long userId, OrderStatus orderStatus);

    OrderEntity findOrderEntitiesById(Long id);
}
