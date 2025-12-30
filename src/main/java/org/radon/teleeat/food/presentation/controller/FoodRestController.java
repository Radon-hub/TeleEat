package org.radon.teleeat.food.presentation.controller;

import org.radon.teleeat.common.dto.PagedResponse;
import org.radon.teleeat.common.dto.Response;
import org.radon.teleeat.food.application.port.in.*;
import org.radon.teleeat.food.presentation.dto.AddFoodRequest;
import org.radon.teleeat.food.presentation.dto.FoodResponse;
import org.radon.teleeat.food.presentation.dto.UpdateFoodRequest;
import org.radon.teleeat.food.presentation.dto.mapper.FoodDtoMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1/foods")
public class FoodRestController {

    private final AddFoodUseCase addFoodUseCase;
    private final RemoveFoodUseCase removeFoodUseCase;
    private final UpdateFoodUseCase updateFoodUseCase;
    private final GetFoodUseCase getFoodUseCase;
    private final GetFoodsPagination getFoodsPagination;
    private final GetFoodsWithFilterPagination getFoodsWithFilterPagination;

    public FoodRestController(AddFoodUseCase addFoodUseCase, RemoveFoodUseCase removeFoodUseCase, UpdateFoodUseCase updateFoodUseCase, GetFoodUseCase getFoodUseCase, GetFoodsPagination getFoodsPagination, GetFoodsWithFilterPagination getFoodsWithFilterPagination) {
        this.addFoodUseCase = addFoodUseCase;
        this.removeFoodUseCase = removeFoodUseCase;
        this.updateFoodUseCase = updateFoodUseCase;
        this.getFoodUseCase = getFoodUseCase;
        this.getFoodsPagination = getFoodsPagination;
        this.getFoodsWithFilterPagination = getFoodsWithFilterPagination;
    }

    @PreAuthorize("hasAnyRole('SUPERVISOR','ADMIN')")
    @PostMapping("add")
    public ResponseEntity<Response<String>> addFood(@RequestBody AddFoodRequest addFoodRequest) {
        addFoodUseCase.addFood(addFoodRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response<>("Food added successfully..."));
    }

    @PreAuthorize("hasAnyRole('SUPERVISOR','ADMIN')")
    @DeleteMapping("{id}/remove")
    public ResponseEntity<Response<String>> deleteFood(@PathVariable Long id) {
        removeFoodUseCase.removeFood(id);
        return ResponseEntity.ok().body(new Response<>("Food removed successfully !"));
    }

    @PreAuthorize("hasAnyRole('SUPERVISOR','ADMIN')")
    @PutMapping("{id}/update")
    public ResponseEntity<Response<FoodResponse>> updateFood(
            @RequestBody UpdateFoodRequest  updateFoodRequest,
            @PathVariable Long id
    ) {
        return ResponseEntity.ok().body(new Response<>(FoodDtoMapper.toFoodResponse(
                updateFoodUseCase.updateFood(
                        updateFoodRequest,
                        id
                )
        )));
    }

    @PreAuthorize("hasAnyRole('SUPERVISOR','ADMIN')")
    @GetMapping("{id}")
    public ResponseEntity<Response<FoodResponse>> getFood(@PathVariable Long id) {
        return ResponseEntity.ok().body(new Response<>(FoodDtoMapper.toFoodResponse(
                getFoodUseCase.getFood(
                        id
                )
        )));
    }

    @PreAuthorize("hasAnyRole('SUPERVISOR','ADMIN')")
    @GetMapping("all")
    public ResponseEntity<PagedResponse<FoodResponse>> getFoodsPagination(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok().body(
                FoodDtoMapper.toPagedFoodResponse(
                        getFoodsPagination.getFoods(pageable)
                )
        );
    }

    @PreAuthorize("hasAnyRole('SUPERVISOR','ADMIN')")
    @GetMapping("filter/all")
    public ResponseEntity<PagedResponse<FoodResponse>> getFoodsPaginationWithFilter(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) BigDecimal price,
            @RequestParam(required = false) LocalDateTime from,
            @RequestParam(required = false) LocalDateTime to,
            @PageableDefault Pageable pageable
    ) {
        return ResponseEntity.ok().body(
                FoodDtoMapper.toPagedFoodResponse(
                        getFoodsWithFilterPagination.getFoodsFilter(name,price, from, to, pageable)
                )
        );
    }

}
