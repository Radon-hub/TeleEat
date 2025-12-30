package org.radon.teleeat.order.infrastructure.adapter.specification;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import lombok.val;
import org.radon.teleeat.food.infrastructure.repository.entity.FoodEntity;
import org.radon.teleeat.order.domain.OrderStatus;
import org.radon.teleeat.order.infrastructure.repository.entity.OrderEntity;
import org.radon.teleeat.order.infrastructure.repository.entity.OrderItemEntity;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public class OrderSpecifications {
    public static Specification<OrderEntity> addressContains(String keyword) {
        return (root,query,cb) ->
                keyword == null ? null : cb.like(
                        cb.lower(root.get("address")),
                        "%" + keyword.toLowerCase() + "%"
                );
    }

    public static Specification<OrderEntity> hasStatusIn(Set<OrderStatus> statuses) {
        return (root, query, cb) -> {
            if (statuses == null || statuses.isEmpty()) {
                return null;
            }
            return root.get("orderStatus").in(statuses);
        };
    }

    public static Specification<OrderEntity> totalPriceTo(BigDecimal maxPrice) {
        return (root, query, cb) -> {
            if (maxPrice == null) {
                return null;
            }

            val items = root.get("items");

            Expression<BigDecimal> totalPrice =
                    cb.sum(
                            cb.prod(
                                    items.get("price"),
                                    items.get("count")
                            )
                    );

            query.groupBy(root.get("id"));
            query.having(cb.lessThanOrEqualTo(totalPrice, maxPrice));
            query.distinct(true);

            return cb.conjunction(); // IMPORTANT

//            return cb.lessThanOrEqualTo(totalPrice, maxPrice);
        };
    }

    public static Specification<OrderEntity> createdBetween(
            LocalDateTime from,
            LocalDateTime to
    ) {
        return (root, query, cb) ->
                (from == null || to == null)
                        ? null
                        : cb.between(root.get("createdAt"), from, to);
    }
}
