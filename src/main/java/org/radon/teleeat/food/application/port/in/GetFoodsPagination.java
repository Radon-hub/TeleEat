package org.radon.teleeat.food.application.port.in;

import org.radon.teleeat.food.domain.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetFoodsPagination {
    Page<Food> getFoods(Pageable pageable);
}
