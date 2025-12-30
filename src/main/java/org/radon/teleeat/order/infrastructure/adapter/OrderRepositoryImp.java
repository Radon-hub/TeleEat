package org.radon.teleeat.order.infrastructure.adapter;

import lombok.val;
import org.radon.teleeat.common.aop.exceptionHandling.OpenOrderExistException;
import org.radon.teleeat.common.aop.exceptionHandling.OrderNotExistException;
import org.radon.teleeat.food.infrastructure.adapter.mapper.FoodMapper;
import org.radon.teleeat.food.infrastructure.repository.entity.FoodEntity;
import org.radon.teleeat.order.application.port.out.OrderRepository;
import org.radon.teleeat.order.domain.Order;
import org.radon.teleeat.order.domain.OrderItem;
import org.radon.teleeat.order.domain.OrderStatus;
import org.radon.teleeat.order.infrastructure.adapter.mapper.OrderMappers;
import org.radon.teleeat.order.infrastructure.repository.OrderJpaRepository;
import org.radon.teleeat.order.infrastructure.repository.entity.OrderEntity;
import org.radon.teleeat.order.infrastructure.repository.entity.OrderItemEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public class OrderRepositoryImp implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;

    public OrderRepositoryImp(OrderJpaRepository orderJpaRepository) {
        this.orderJpaRepository = orderJpaRepository;
    }

    @Override
    public Order getOrder(Long id) {
        OrderEntity orderEntity = orderJpaRepository.findById(id).orElseThrow(OrderNotExistException::new);
        return OrderMappers.fromOrderEntityToOrder(orderEntity);
    }

    @Override
    public Order getOpenOrder(Long userId) {
        OrderEntity orderEntity = orderJpaRepository.findOrderEntityByUserIdAndOrderStatus(userId,OrderStatus.CREATED);
        if(orderEntity==null){
            return null;
        }else{
            return OrderMappers.fromOrderEntityToOrder(
                    orderEntity
            );
        }
    }

    @Override
    public Order create(Order order) {
        return OrderMappers.fromOrderEntityToOrder(orderJpaRepository.save(new OrderEntity(
                order.getUserId()
        )));
    }

    @Override
    public Order updateOrderStatus(Long orderId, OrderStatus orderStatus) {
        OrderEntity orderEntity = orderJpaRepository.findOrderEntitiesById(orderId);
        orderEntity.setOrderStatus(orderStatus);
        return OrderMappers.fromOrderEntityToOrder(orderEntity);
    }

    @Override
    public Order updateAddress(Long orderId, String address) {
        OrderEntity orderEntity = orderJpaRepository.findOrderEntitiesById(orderId);
        orderEntity.setAddress(address);
        return OrderMappers.fromOrderEntityToOrder(orderEntity);
    }

    @Override
    public void save(Order order) {
        OrderEntity orderEntity = orderJpaRepository.findById(order.getId()).orElseThrow(OrderNotExistException::new);

        List<OrderItemEntity> mergedItems = new ArrayList<>();
        for (OrderItem domainItem : order.getItems()) {

            OrderItemEntity itemEntity = orderEntity.getItems().stream().filter(i -> i.getId().equals(domainItem.getId())).findFirst().orElse(null);
            if(itemEntity != null){
                itemEntity.setCount(domainItem.getCount());
            }else{
                itemEntity = new OrderItemEntity(new FoodEntity(
                        domainItem.getFood().getId(),
                        domainItem.getFood().getName(),
                        domainItem.getFood().getDescription(),
                        domainItem.getFood().getPrice(),
                        domainItem.getFood().getCreatedAt(),
                        domainItem.getFood().getUpdatedAt()
                ),orderEntity,domainItem.getFood().getPrice());
            }
            mergedItems.add(itemEntity);
        }

        orderEntity.getItems().clear();
        orderEntity.getItems().addAll(mergedItems);

        orderJpaRepository.save(orderEntity);
    }
}
