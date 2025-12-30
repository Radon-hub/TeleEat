package org.radon.teleeat.food.application.port.in;

import org.radon.teleeat.food.domain.Food;
import org.radon.teleeat.food.presentation.dto.AddFoodRequest;

public interface AddFoodUseCase {
    void addFood(AddFoodRequest addFoodRequest);
}
