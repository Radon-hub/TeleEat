package org.radon.teleeat.food.application.port.in;

import org.radon.teleeat.food.domain.Food;

public interface UpdateFoodUseCase {
    Food updateFood(Food food);
}
