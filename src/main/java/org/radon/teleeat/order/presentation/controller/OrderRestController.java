package org.radon.teleeat.order.presentation.controller;

import lombok.val;
import org.radon.teleeat.common.dto.Response;
import org.radon.teleeat.order.application.port.in.*;
import org.radon.teleeat.order.presentation.dto.AddOrderItemRequest;
import org.radon.teleeat.order.presentation.dto.AddOrderRequest;
import org.radon.teleeat.order.presentation.dto.OrderResponse;
import org.radon.teleeat.order.presentation.mapper.OrderDtoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/order")
public class OrderRestController {

    private final AddOrGetOrderUseCase addOrderUseCase;
    private final RemoveOrderUseCase removeOrderUseCase;
    private final GetOrderUseCase getOrderUseCase;
    private final AddOrderItemUseCase addOrderItemUseCase;
    private final RemoveOrderItemUseCase removeOrderItemUseCase;

    public OrderRestController(AddOrGetOrderUseCase addOrderUseCase, RemoveOrderUseCase removeOrderUseCase, GetOrderUseCase getOrderUseCase, AddOrderItemUseCase addOrderItemUseCase, RemoveOrderItemUseCase removeOrderItemUseCase) {
        this.addOrderUseCase = addOrderUseCase;
        this.removeOrderUseCase = removeOrderUseCase;
        this.getOrderUseCase = getOrderUseCase;
        this.addOrderItemUseCase = addOrderItemUseCase;
        this.removeOrderItemUseCase = removeOrderItemUseCase;
    }
    
    @PostMapping("add")
    public ResponseEntity<Response<String>> addOrder(@RequestBody AddOrderRequest addOrderRequest) {
        val Order = addOrderUseCase.addOrGetOrder(OrderDtoMapper.fromAddOrderRequest(addOrderRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response<>(Order.getId().toString()));
    }

    @DeleteMapping("{id}/remove")
    public ResponseEntity<Response<String>> deleteOrder(@PathVariable Long id) {
        removeOrderUseCase.removeOrder(OrderDtoMapper.fromOrderIdRequest(id));
        return ResponseEntity.ok(new Response<>("Order Remove successfully..."));
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<OrderResponse>> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(new Response<>(OrderDtoMapper.fromOrder(getOrderUseCase.getOrder(OrderDtoMapper.fromOrderIdRequest(id)))));
    }

    @PostMapping("item/add")
    public ResponseEntity<Response<String>> addOrderItem(@RequestBody AddOrderItemRequest addOrderItemRequest) {
        addOrderItemUseCase.addOrderItem(OrderDtoMapper.fromAddOrderItemRequest(addOrderItemRequest));
    }
    
}
