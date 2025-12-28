package org.radon.teleeat.food.application.port.in;

import org.radon.teleeat.food.domain.Food;

public interface RemoveFoodUseCase {
    void removeFood(Food food);
}
