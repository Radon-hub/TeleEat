package org.radon.teleeat.order.application.service;

import jakarta.transaction.Transactional;
import org.radon.teleeat.common.aop.exceptionHandling.OrderItemNotFoundException;
import org.radon.teleeat.common.aop.exceptionHandling.OrderNotExistException;
import org.radon.teleeat.common.aop.exceptionHandling.UserNotFound;
import org.radon.teleeat.food.application.port.out.FoodRepository;
import org.radon.teleeat.food.domain.Food;
import org.radon.teleeat.order.application.port.in.AddOrderItemUseCase;
import org.radon.teleeat.order.application.port.in.GetUserOpenOrderUseCase;
import org.radon.teleeat.order.application.port.in.RemoveOrderItemUseCase;
import org.radon.teleeat.order.application.port.out.OrderRepository;
import org.radon.teleeat.order.domain.Order;
import org.radon.teleeat.order.presentation.dto.AddOrderItemRequest;
import org.radon.teleeat.order.presentation.dto.RemoveOrderItemRequest;
import org.radon.teleeat.user.application.port.out.UserRepository;
import org.radon.teleeat.user.domain.User;
import org.springframework.stereotype.Service;

@Service
public class OrderItemService implements AddOrderItemUseCase, RemoveOrderItemUseCase, GetUserOpenOrderUseCase {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final FoodRepository foodRepository;


    public OrderItemService(UserRepository userRepository, OrderRepository orderRepository, FoodRepository foodRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.foodRepository = foodRepository;
    }


    @Transactional
    @Override
    public void addOrderItem(AddOrderItemRequest addOrderItemRequest) {

        User user = userRepository.getUser(addOrderItemRequest.getUserId());

        Order order = orderRepository.getOpenOrder(addOrderItemRequest.getUserId());

        if(order==null){
            order = orderRepository.create(new Order.Builder().user(user).build());
        }

        Food food = foodRepository.getFood(addOrderItemRequest.getFoodId());

        order.addItem(food);

        orderRepository.save(order);
    }

    @Transactional
    @Override
    public void removeOrderItem(RemoveOrderItemRequest removeOrderItemRequest) {
        Order order = orderRepository.getOpenOrder(removeOrderItemRequest.getUserId());

        if(order==null){
            throw new OrderNotExistException();
        }

        if(order.getItems().isEmpty()){
            throw new OrderItemNotFoundException();
        }

        order.removeItem(removeOrderItemRequest.getOrderItemId());

        orderRepository.save(order);
    }

    @Transactional
    @Override
    public Order getOpenOrder(Long userId) {
        return orderRepository.getOpenOrder(userId);
    }
}
