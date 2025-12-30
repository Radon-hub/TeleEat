package org.radon.teleeat.order.presentation.controller;

import lombok.val;
import org.radon.teleeat.common.dto.PagedResponse;
import org.radon.teleeat.common.dto.Response;
import org.radon.teleeat.food.presentation.dto.FoodResponse;
import org.radon.teleeat.food.presentation.dto.mapper.FoodDtoMapper;
import org.radon.teleeat.order.application.port.in.*;
import org.radon.teleeat.order.domain.OrderStatus;
import org.radon.teleeat.order.presentation.dto.AddOrderItemRequest;
import org.radon.teleeat.order.presentation.dto.OrderResponse;
import org.radon.teleeat.order.presentation.dto.RemoveOrderItemRequest;
import org.radon.teleeat.order.presentation.mapper.OrderDtoMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@RestController
@RequestMapping("api/v1/order")
public class OrderRestController {

    private final GetOrderUseCase getOrderUseCase;
    private final AddOrderItemUseCase addOrderItemUseCase;
    private final RemoveOrderItemUseCase removeOrderItemUseCase;
    private final GetOrdersWithPaginationFilter getOrdersWithPaginationFilter;

    public OrderRestController(GetOrderUseCase getOrderUseCase, AddOrderItemUseCase addOrderItemUseCase, RemoveOrderItemUseCase removeOrderItemUseCase, GetOrdersWithPaginationFilter getOrdersWithPaginationFilter) {
        this.getOrderUseCase = getOrderUseCase;
        this.addOrderItemUseCase = addOrderItemUseCase;
        this.removeOrderItemUseCase = removeOrderItemUseCase;
        this.getOrdersWithPaginationFilter = getOrdersWithPaginationFilter;
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<OrderResponse>> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(new Response<>(OrderDtoMapper.fromOrder(getOrderUseCase.getOrder(id))));
    }

    @PostMapping("item/add")
    public ResponseEntity<Response<String>> addOrderItem(@RequestBody AddOrderItemRequest addOrderItemRequest) {
        addOrderItemUseCase.addOrderItem(addOrderItemRequest);
        return ResponseEntity.ok(new Response<>("Food added to order successfully..."));
    }

    @DeleteMapping("item/remove")
    public ResponseEntity<Response<String>> removeOrderItem(@RequestBody RemoveOrderItemRequest removeOrderItemRequest) {
        removeOrderItemUseCase.removeOrderItem(removeOrderItemRequest);
        return ResponseEntity.ok(new Response<>("Food removed from order..."));
    }

    @PreAuthorize("hasAnyRole('SUPERVISOR','ADMIN')")
    @GetMapping("filter/all")
    public ResponseEntity<PagedResponse<OrderResponse>> getOrdersPaginationWithFilter(
            @RequestParam(required = false) String address,
            @RequestParam(required = false) BigDecimal price,
            @RequestParam(required = false) Set<OrderStatus> statuses,
            @RequestParam(required = false) LocalDateTime from,
            @RequestParam(required = false) LocalDateTime to,
            @PageableDefault Pageable pageable
    ) {
        return ResponseEntity.ok().body(
                OrderDtoMapper.toPagedOrderResponse(
                        getOrdersWithPaginationFilter.getOrderWithFilter(address,price,statuses, from, to, pageable)
                )
        );
    }
}
