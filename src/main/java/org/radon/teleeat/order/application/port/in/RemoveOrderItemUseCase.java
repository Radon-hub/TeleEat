package org.radon.teleeat.order.application.port.in;

import org.radon.teleeat.order.domain.OrderItem;
import org.radon.teleeat.order.presentation.dto.RemoveOrderItemRequest;

public interface RemoveOrderItemUseCase {
    void removeOrderItem(RemoveOrderItemRequest removeOrderItemRequest);
}
