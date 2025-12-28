package org.radon.teleeat.food.application.port.in;

import org.radon.teleeat.food.domain.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface GetFoodsWithFilterPagination {
    Page<Food> getFoodsFilter(String name, BigDecimal priceTo, LocalDateTime from , LocalDateTime to, Pageable pageable);
}
