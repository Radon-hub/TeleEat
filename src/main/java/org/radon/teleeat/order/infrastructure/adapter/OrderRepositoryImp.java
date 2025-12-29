package org.radon.teleeat.order.infrastructure.adapter;

import lombok.val;
import org.radon.teleeat.common.aop.exceptionHandling.OpenOrderExistException;
import org.radon.teleeat.common.aop.exceptionHandling.OrderNotExistException;
import org.radon.teleeat.order.application.port.out.OrderRepository;
import org.radon.teleeat.order.domain.Order;
import org.radon.teleeat.order.domain.OrderStatus;
import org.radon.teleeat.order.infrastructure.adapter.mapper.OrderMappers;
import org.radon.teleeat.order.infrastructure.repository.OrderJpaRepository;
import org.radon.teleeat.order.infrastructure.repository.entity.OrderEntity;
import org.springframework.stereotype.Repository;


@Repository
public class OrderRepositoryImp implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;

    public OrderRepositoryImp(OrderJpaRepository orderJpaRepository) {
        this.orderJpaRepository = orderJpaRepository;
    }

    @Override
    public Order getOrder(Order order) {
        OrderEntity orderEntity = orderJpaRepository.findById(order.getId()).orElseThrow(OrderNotExistException::new);
        return OrderMappers.fromOrderEntityToOrder(orderEntity);
    }
}
