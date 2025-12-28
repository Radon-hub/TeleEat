package org.radon.teleeat.food.application.service;

import org.radon.teleeat.food.application.port.in.*;
import org.radon.teleeat.food.application.port.out.FoodRepository;
import org.radon.teleeat.food.domain.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class FoodService implements AddFoodUseCase, UpdateFoodUseCase, RemoveFoodUseCase, GetFoodUseCase, GetFoodsPagination,GetFoodsWithFilterPagination {

    private final FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @Override
    public void addFood(Food food) {
        foodRepository.addFood(food);
    }

    @Override
    public void removeFood(Food food) {
        foodRepository.removeFood(food);
    }

    @Override
    public Food updateFood(Food food) {
        return foodRepository.updateFood(food);
    }

    @Override
    public Food getFood(Food food) {
        return  foodRepository.getFood(food);
    }

    @Override
    public Page<Food> getFoods(Pageable pageable) {
        return foodRepository.getFoods(pageable);
    }

    @Override
    public Page<Food> getFoodsFilter(String name, BigDecimal priceTo, LocalDateTime from, LocalDateTime to, Pageable pageable) {
        return foodRepository.getFoodsFilter(name,priceTo, from, to, pageable);
    }
}
