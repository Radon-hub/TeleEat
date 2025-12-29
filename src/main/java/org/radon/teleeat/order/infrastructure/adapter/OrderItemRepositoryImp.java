package org.radon.teleeat.order.infrastructure.adapter;

import jakarta.transaction.Transactional;
import lombok.val;
import org.radon.teleeat.common.aop.exceptionHandling.FoodNotExistException;
import org.radon.teleeat.common.aop.exceptionHandling.OrderItemNotFoundException;
import org.radon.teleeat.common.aop.exceptionHandling.OrderNotExistException;
import org.radon.teleeat.common.aop.exceptionHandling.UserNotFound;
import org.radon.teleeat.food.application.port.in.GetFoodUseCase;
import org.radon.teleeat.food.infrastructure.adapter.mapper.FoodMapper;
import org.radon.teleeat.food.infrastructure.repository.FoodJpaRepository;
import org.radon.teleeat.food.infrastructure.repository.entity.FoodEntity;
import org.radon.teleeat.order.application.port.out.OrderItemRepository;
import org.radon.teleeat.order.domain.OrderItem;
import org.radon.teleeat.order.domain.OrderStatus;
import org.radon.teleeat.order.infrastructure.adapter.mapper.OrderMappers;
import org.radon.teleeat.order.infrastructure.repository.OrderItemsJpaRepository;
import org.radon.teleeat.order.infrastructure.repository.OrderJpaRepository;
import org.radon.teleeat.order.infrastructure.repository.entity.OrderEntity;
import org.radon.teleeat.order.infrastructure.repository.entity.OrderItemEntity;
import org.radon.teleeat.user.infrastructure.repository.UserJpaRepository;
import org.radon.teleeat.user.infrastructure.repository.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public class OrderItemRepositoryImp implements OrderItemRepository {

    private final OrderItemsJpaRepository orderItemsJpaRepository;
    private final OrderJpaRepository orderJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final FoodJpaRepository foodJpaRepository;

    public OrderItemRepositoryImp(OrderItemsJpaRepository orderItemsJpaRepository, OrderJpaRepository orderJpaRepository, UserJpaRepository userJpaRepository, FoodJpaRepository foodJpaRepository) {
        this.orderItemsJpaRepository = orderItemsJpaRepository;
        this.orderJpaRepository = orderJpaRepository;
        this.userJpaRepository = userJpaRepository;
        this.foodJpaRepository = foodJpaRepository;
    }

    @Transactional
    @Override
    public void addOrderItem(OrderItem orderItem) {

        UserEntity user = userJpaRepository.findById(orderItem.getOrder().getUserId()).orElseThrow(UserNotFound::new);

        OrderEntity orderEntity = orderJpaRepository.findOrderEntityByUserIdAndOrderStatus(user.getId(), OrderStatus.CREATED);

        if(orderEntity == null){

            orderEntity = orderJpaRepository.save(OrderMappers.orderEntityFromUserId(orderItem.getOrder().getUserId()));

        }

        if(!orderEntity.getItems().isEmpty()){

            Optional<OrderItemEntity> existingItem = orderEntity.getItems().stream()
                    .filter(i -> i.getFood().getId().equals(orderItem.getFood().getId()))
                    .findFirst();

            if (existingItem.isPresent()) {
                OrderItemEntity itemEntity = existingItem.get();
                itemEntity.setCount((byte) (itemEntity.getCount() + 1));
                return;
            }

        }

        FoodEntity foodEntity = foodJpaRepository.findById(orderItem.getFood().getId()).orElseThrow(FoodNotExistException::new);

        orderEntity.getItems().add(new OrderItemEntity(
                foodEntity,
                orderEntity,
                foodEntity.getPrice()
        ));

    }

    @Transactional
    @Override
    public void removeOrderItem(OrderItem orderItem) {

        OrderItemEntity item = orderItemsJpaRepository
                .findById(orderItem.getId())
                .orElseThrow(OrderItemNotFoundException::new);

        if(!orderItem.getOrder().getUserId().equals(item.getOrder().getUserId())){
            throw new OrderItemNotFoundException();
        }

        if (item.getCount() > 1) {
            item.setCount((byte) (item.getCount() - 1));
        } else {
            orderItemsJpaRepository.delete(item);
        }
    }

}
