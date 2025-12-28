package org.radon.teleeat.food.infrastructure.repository;

import org.radon.teleeat.food.infrastructure.repository.entity.FoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface FoodJpaRepository extends JpaRepository<FoodEntity,Long>, JpaSpecificationExecutor<FoodEntity> {
    Optional<FoodEntity> findFoodEntityByName(String name);
}
