package org.radon.teleeat.food.application.service;

import org.radon.teleeat.food.application.port.in.*;
import org.radon.teleeat.food.application.port.out.FoodRepository;
import org.radon.teleeat.food.domain.Food;
import org.radon.teleeat.food.presentation.dto.AddFoodRequest;
import org.radon.teleeat.food.presentation.dto.UpdateFoodRequest;
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
    public void addFood(AddFoodRequest addFoodRequest) {
        foodRepository.addFood(addFoodRequest);
    }

    @Override
    public void removeFood(Long id) {
        foodRepository.removeFood(id);
    }

    @Override
    public Food updateFood(
            UpdateFoodRequest updateFoodRequest,
            Long id
    ) {
        return foodRepository.updateFood(
                updateFoodRequest,
                id
        );
    }

    @Override
    public Food getFood(Long id) {
        return  foodRepository.getFood(id);
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
