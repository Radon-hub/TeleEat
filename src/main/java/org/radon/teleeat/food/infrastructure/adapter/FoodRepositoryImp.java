package org.radon.teleeat.food.infrastructure.adapter;

import org.radon.teleeat.common.aop.exceptionHandling.FoodExistException;
import org.radon.teleeat.common.aop.exceptionHandling.FoodNotExistException;
import org.radon.teleeat.food.application.port.out.FoodRepository;
import org.radon.teleeat.food.domain.Food;
import org.radon.teleeat.food.infrastructure.adapter.mapper.FoodMapper;
import org.radon.teleeat.food.infrastructure.repository.FoodJpaRepository;
import org.radon.teleeat.food.infrastructure.repository.specifications.FoodSpecifications;
import org.radon.teleeat.food.infrastructure.repository.entity.FoodEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class FoodRepositoryImp implements FoodRepository {

    private final FoodJpaRepository foodJpaRepository;

    public FoodRepositoryImp(FoodJpaRepository foodJpaRepository) {
        this.foodJpaRepository = foodJpaRepository;
    }

    @Override
    public void addFood(Food food) {

        Optional<FoodEntity> foodEntity = foodJpaRepository.findFoodEntityByName(food.getName());

        if(foodEntity.isPresent()){
            throw new FoodExistException();
        }


        foodJpaRepository.save(FoodMapper.fromFood(food));

    }

    @Override
    public Food updateFood(Food food) {

        FoodEntity foodEntity = foodJpaRepository.findById(food.getId()).orElseThrow(FoodNotExistException::new);

        foodEntity.setName(food.getName());
        foodEntity.setDescription(food.getDescription());
        foodEntity.setPrice(food.getPrice());
        foodEntity.setUpdatedAt(LocalDateTime.now());

        foodJpaRepository.save(foodEntity);

        return FoodMapper.fromFoodEntity(foodEntity);
    }

    @Override
    public void removeFood(Food food) {
        FoodEntity foodEntity = foodJpaRepository.findById(food.getId()).orElseThrow(FoodNotExistException::new);
        foodJpaRepository.delete(foodEntity);
    }

    @Override
    public Page<Food> getFoods(Pageable pageable) {

        Page<FoodEntity> foodEntityPage = foodJpaRepository.findAll(pageable);

        return foodEntityPage.map(FoodMapper::fromFoodEntity);

    }

    @Override
    public Food getFood(Food food) {
        FoodEntity foodEntity = foodJpaRepository.findById(food.getId()).orElseThrow(FoodNotExistException::new);
        return FoodMapper.fromFoodEntity(foodEntity);
    }

    @Override
    public Page<Food> getFoodsFilter(String name, BigDecimal priceTo, LocalDateTime from, LocalDateTime to, Pageable pageable) {
        Specification<FoodEntity> spec = Specification
                .where(FoodSpecifications.nameContains(name))
                .and(FoodSpecifications.priceTo(priceTo))
                .and(FoodSpecifications.createdBetween(from,to));

        return foodJpaRepository.findAll(spec,pageable).map(FoodMapper::fromFoodEntity);
    }
}
