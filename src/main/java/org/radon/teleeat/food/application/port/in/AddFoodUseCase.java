package org.radon.teleeat.food.application.port.in;

import org.radon.teleeat.food.domain.Food;

public interface AddFoodUseCase {
    void addFood(Food food);
}
