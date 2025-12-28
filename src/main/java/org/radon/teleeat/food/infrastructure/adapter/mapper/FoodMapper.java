package org.radon.teleeat.food.infrastructure.adapter.mapper;

import org.radon.teleeat.food.domain.Food;
import org.radon.teleeat.food.infrastructure.repository.entity.FoodEntity;

import java.time.LocalDateTime;

public class FoodMapper {



    public static Food fromFoodEntity(FoodEntity foodEntity) {
        return new Food.Builder()
                .id(foodEntity.getId())
                .name(foodEntity.getName())
                .description(foodEntity.getDescription())
                .price(foodEntity.getPrice())
                .createdAt(foodEntity.getCreatedAt())
                .updatedAt(foodEntity.getUpdatedAt())
                .build();
    }

    public static FoodEntity fromFood(Food food) {
        return new FoodEntity(
                food.getName(),
                food.getDescription(),
                food.getPrice(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

}
