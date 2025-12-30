package org.radon.teleeat.food.application.port.out;

import org.radon.teleeat.food.domain.Food;
import org.radon.teleeat.food.presentation.dto.AddFoodRequest;
import org.radon.teleeat.food.presentation.dto.UpdateFoodRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface FoodRepository {
    void addFood(AddFoodRequest addFoodRequest);
    Food updateFood(
            UpdateFoodRequest updateFoodRequest,
            Long id
    );
    void removeFood(Long id);
    Page<Food> getFoods(Pageable pageable);
    Food getFood(Long id);
    Page<Food> getFoodsFilter(String name, BigDecimal priceTo, LocalDateTime from , LocalDateTime to, Pageable pageable);

}
