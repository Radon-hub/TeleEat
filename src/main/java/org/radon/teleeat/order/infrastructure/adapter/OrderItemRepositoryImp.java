package org.radon.teleeat.order.infrastructure.adapter;

import jakarta.transaction.Transactional;
import lombok.val;
import org.radon.teleeat.common.aop.exceptionHandling.OrderItemNotFoundException;
import org.radon.teleeat.common.aop.exceptionHandling.OrderNotExistException;
import org.radon.teleeat.order.application.port.in.AddOrGetOrderUseCase;
import org.radon.teleeat.order.application.port.out.OrderItemRepository;
import org.radon.teleeat.order.domain.OrderItem;
import org.radon.teleeat.order.domain.OrderStatus;
import org.radon.teleeat.order.infrastructure.adapter.mapper.OrderMappers;
import org.radon.teleeat.order.infrastructure.repository.OrderItemsJpaRepository;
import org.radon.teleeat.order.infrastructure.repository.OrderJpaRepository;
import org.radon.teleeat.order.infrastructure.repository.entity.OrderEntity;
import org.radon.teleeat.order.infrastructure.repository.entity.OrderItemEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public class OrderItemRepositoryImp implements OrderItemRepository {

    private final OrderItemsJpaRepository orderItemsJpaRepository;
    private final AddOrGetOrderUseCase addOrGetOrderUseCase;


    public OrderItemRepositoryImp(OrderItemsJpaRepository orderItemsJpaRepository, AddOrGetOrderUseCase addOrGetOrderUseCase) {
        this.orderItemsJpaRepository = orderItemsJpaRepository;
        this.addOrGetOrderUseCase = addOrGetOrderUseCase;
    }

    @Transactional
    @Override
    public void addOrderItem(OrderItem orderItem) {

        val userOrder = addOrGetOrderUseCase.addOrGetOrder(orderItem.getOrder());

        if(userOrder == null) {
            throw new OrderNotExistException();
        }
        
        orderItemsJpaRepository.save(OrderMappers.fromOrderItemToOrderItemEntity(orderItem));
    }

    @Override
    public void removeOrderItem(OrderItem orderItem) {

        OrderItemEntity item = orderItemsJpaRepository.findById(orderItem.getId()).orElseThrow(OrderItemNotFoundException::new);

        orderItemsJpaRepository.delete(item);

    }
}
