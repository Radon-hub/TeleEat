package org.radon.teleeat.food.presentation.dto.mapper;

import org.radon.teleeat.common.dto.PagedResponse;
import org.radon.teleeat.food.domain.Food;
import org.radon.teleeat.food.presentation.dto.AddFoodRequest;
import org.radon.teleeat.food.presentation.dto.FoodResponse;
import org.radon.teleeat.food.presentation.dto.UpdateFoodRequest;
import org.springframework.data.domain.Page;

public class FoodDtoMapper {

    public static FoodResponse toFoodResponse(Food food) {
        return new FoodResponse(
                food.getName(),
                food.getDescription(),
                food.getPrice(),
                food.getCreatedAt(),
                food.getUpdatedAt()
        );
    }

    public static Food fromAddFoodRequest(AddFoodRequest addFoodRequest) {
        return new Food.Builder()
                .name(addFoodRequest.getName())
                .description(addFoodRequest.getDescription())
                .price(addFoodRequest.getPrice())
                .build();
    }

    public static Food fromIdFoodRequest(Long id) {
        return new Food.Builder()
                .id(id)
                .build();
    }

    public static PagedResponse<FoodResponse> toPagedFoodResponse(Page<Food> foods) {
        return new PagedResponse<>(
                foods.getContent().stream().map(FoodDtoMapper::toFoodResponse).toList(),
                foods.getNumber(),
                foods.getSize(),
                foods.getTotalPages(),
                foods.getTotalElements()
        );
    }

    public static Food fromUpdateFoodRequest(Long id,UpdateFoodRequest updateFoodRequest) {
        return new Food.Builder()
                .id(id)
                .name(updateFoodRequest.getName())
                .description(updateFoodRequest.getDescription())
                .price(updateFoodRequest.getPrice())
                .build();
    }

}
