package org.radon.teleeat.order.presentation.controller;

import lombok.val;
import org.radon.teleeat.common.dto.Response;
import org.radon.teleeat.order.application.port.in.*;
import org.radon.teleeat.order.presentation.dto.AddOrderItemRequest;
import org.radon.teleeat.order.presentation.dto.OrderResponse;
import org.radon.teleeat.order.presentation.dto.RemoveOrderItemRequest;
import org.radon.teleeat.order.presentation.mapper.OrderDtoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/order")
public class OrderRestController {

    private final GetOrderUseCase getOrderUseCase;
    private final AddOrderItemUseCase addOrderItemUseCase;
    private final RemoveOrderItemUseCase removeOrderItemUseCase;

    public OrderRestController(GetOrderUseCase getOrderUseCase, AddOrderItemUseCase addOrderItemUseCase, RemoveOrderItemUseCase removeOrderItemUseCase) {
        this.getOrderUseCase = getOrderUseCase;
        this.addOrderItemUseCase = addOrderItemUseCase;
        this.removeOrderItemUseCase = removeOrderItemUseCase;
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<OrderResponse>> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(new Response<>(OrderDtoMapper.fromOrder(getOrderUseCase.getOrder(OrderDtoMapper.fromOrderIdRequest(id)))));
    }

    @PostMapping("item/add")
    public ResponseEntity<Response<String>> addOrderItem(@RequestBody AddOrderItemRequest addOrderItemRequest) {
        addOrderItemUseCase.addOrderItem(OrderDtoMapper.fromAddOrderItemRequest(addOrderItemRequest));
        return ResponseEntity.ok(new Response<>("Food added to order successfully..."));
    }

    @DeleteMapping("item/remove")
    public ResponseEntity<Response<String>> removeOrderItem(@RequestBody RemoveOrderItemRequest removeOrderItemRequest) {
        removeOrderItemUseCase.removeOrderItem(OrderDtoMapper.fromIdToRemoveOrderItemRequest(removeOrderItemRequest));
        return ResponseEntity.ok(new Response<>("Food removed from order..."));
    }
}
