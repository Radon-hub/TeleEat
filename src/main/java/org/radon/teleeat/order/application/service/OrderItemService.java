package org.radon.teleeat.order.application.service;

import jakarta.transaction.Transactional;
import org.radon.teleeat.common.aop.exceptionHandling.OrderItemNotFoundException;
import org.radon.teleeat.food.application.port.out.FoodRepository;
import org.radon.teleeat.food.domain.Food;
import org.radon.teleeat.order.application.port.in.AddOrderItemUseCase;
import org.radon.teleeat.order.application.port.in.RemoveOrderItemUseCase;
import org.radon.teleeat.order.application.port.out.OrderRepository;
import org.radon.teleeat.order.domain.Order;
import org.radon.teleeat.order.domain.OrderItem;
import org.radon.teleeat.user.application.port.out.UserRepository;
import org.radon.teleeat.user.domain.User;
import org.springframework.stereotype.Service;

@Service
public class OrderItemService implements AddOrderItemUseCase, RemoveOrderItemUseCase {

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
    public void addOrderItem(OrderItem orderItem) {

        User user = userRepository.getUser(orderItem.getOrder().getUserId());

        Order order = orderRepository.getOpenOrder(new Order.Builder().userId(user.getId()).build());

        if(order == null){
            order = orderRepository.create(new Order.Builder().userId(user.getId()).build());
        }

        Food food = foodRepository.getFood(orderItem.getFood());

        order.addItem(food);

        orderRepository.save(order);
    }

    @Override
    public void removeOrderItem(OrderItem orderItem) {
        Order order = orderRepository.getOpenOrder(orderItem.getOrder());

        if (!order.belongsTo(orderItem.getOrder().getUserId())) {
            throw new OrderItemNotFoundException();
        }

        order.removeItem(orderItem.getId());

        orderRepository.save(order);
    }
}
