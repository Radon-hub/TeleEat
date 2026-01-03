package org.radon.teleeat.integration.telegram.application.service.usecase;

import lombok.val;
import org.radon.teleeat.integration.telegram.application.port.in.SendMessagesUseCase;
import org.radon.teleeat.order.application.port.in.GetUserOpenOrderUseCase;
import org.radon.teleeat.order.application.port.in.UpdateOrderAddressUseCase;
import org.radon.teleeat.order.application.port.in.UpdateOrderStatusUseCase;
import org.radon.teleeat.order.domain.Order;
import org.radon.teleeat.order.domain.OrderStatus;
import org.radon.teleeat.user.application.port.in.GetOrAddUserUseCase;
import org.springframework.stereotype.Component;

@Component
public class AddressFlowUseCase {

    private final GetUserOpenOrderUseCase getUserOpenOrderUseCase;
    private final UpdateOrderAddressUseCase updateOrderAddressUseCase;
    private final GetOrAddUserUseCase getOrAddUserUseCase;
    private final UpdateOrderStatusUseCase updateOrderStatusUseCase;
    private final SendMessagesUseCase sendMessagesUseCase;


    public AddressFlowUseCase(GetUserOpenOrderUseCase getUserOpenOrderUseCase, UpdateOrderAddressUseCase updateOrderAddressUseCase, GetOrAddUserUseCase getOrAddUserUseCase, UpdateOrderStatusUseCase updateOrderStatusUseCase, SendMessagesUseCase sendMessagesUseCase) {
        this.getUserOpenOrderUseCase = getUserOpenOrderUseCase;
        this.updateOrderAddressUseCase = updateOrderAddressUseCase;
        this.getOrAddUserUseCase = getOrAddUserUseCase;
        this.updateOrderStatusUseCase = updateOrderStatusUseCase;
        this.sendMessagesUseCase = sendMessagesUseCase;
    }


    public void execute(Long chatId,String text) {
        val user = getOrAddUserUseCase.getOrAdd(chatId.toString());
        val openOrder = getUserOpenOrderUseCase.getOpenOrder(user.getId());

        val userAddress = text.split(":")[1];

        updateOrderAddressUseCase.updateAddress(openOrder.getId(), userAddress);

        submitOrderAndDeliver(chatId,openOrder);
    }

    private void submitOrderAndDeliver(Long chatId, Order openOrder){

//          TODO : WE CAN HAVE A PAYMENT HERE
//          updateOrderStatusUseCase.updateOrderStatus(openOrder.getId(), OrderStatus.PENDING_PAYMENT);

        updateOrderStatusUseCase.updateOrderStatus(openOrder.getId(), OrderStatus.DELIVERY);

        sendMessagesUseCase.sendMessage(chatId, "Thank you for submitting your order! \n\n Your food is on delivery now. \n\n Enjoy your food with TeleEat ;-) \n\n Bye...");
    }

}
