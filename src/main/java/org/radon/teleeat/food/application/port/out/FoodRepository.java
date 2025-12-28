package org.radon.teleeat.food.application.port.out;

import org.radon.teleeat.food.domain.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface FoodRepository {
    void addFood(Food food);
    Food updateFood(Food food);
    void removeFood(Food food);
    Page<Food> getFoods(Pageable pageable);
    Food getFood(Food food);
    Page<Food> getFoodsFilter(String name, BigDecimal priceTo, LocalDateTime from , LocalDateTime to, Pageable pageable);

}
