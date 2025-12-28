package org.radon.teleeat.food.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddFoodRequest {
    private String name;
    private String description;
    private BigDecimal price;
}
