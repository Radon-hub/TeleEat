package org.radon.teleeat.food.infrastructure.repository.specifications;

import org.radon.teleeat.food.infrastructure.repository.entity.FoodEntity;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FoodSpecifications {


    public static Specification<FoodEntity> nameContains(String keyword) {
        return (root,query,cb) ->
                keyword == null ? null : cb.like(
                        cb.lower(root.get("name")),
                        "%" + keyword.toLowerCase() + "%"
                );
    }

    public static Specification<FoodEntity> priceTo(BigDecimal price) {
        return (root,query,cb) ->
                price == null ? null
                        : cb.between(root.get("price"), BigDecimal.valueOf(0), price);
    }

    public static Specification<FoodEntity> createdBetween(
            LocalDateTime from,
            LocalDateTime to
    ) {
        return (root, query, cb) ->
                (from == null || to == null)
                        ? null
                        : cb.between(root.get("createdAt"), from, to);
    }


}
