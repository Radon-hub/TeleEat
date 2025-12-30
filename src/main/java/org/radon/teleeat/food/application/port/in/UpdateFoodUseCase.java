package org.radon.teleeat.food.application.port.in;

import org.radon.teleeat.food.domain.Food;
import org.radon.teleeat.food.presentation.dto.UpdateFoodRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface UpdateFoodUseCase {
    Food updateFood(
            UpdateFoodRequest updateFoodRequest,
            Long id
    );
}
