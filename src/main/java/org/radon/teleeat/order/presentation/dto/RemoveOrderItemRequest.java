package org.radon.teleeat.order.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemoveOrderItemRequest {
    private Long orderItemId;
    private Long userId;
}
