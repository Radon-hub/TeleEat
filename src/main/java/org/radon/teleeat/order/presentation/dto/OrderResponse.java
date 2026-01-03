package org.radon.teleeat.order.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.radon.teleeat.order.domain.OrderStatus;
import org.radon.teleeat.user.presentation.dto.UserResponse;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Long id;
    private OrderStatus status;
    private String address;
    private UserResponse user;
    private List<OrderItemResponse> items;
    private BigDecimal totalPrice;
}
