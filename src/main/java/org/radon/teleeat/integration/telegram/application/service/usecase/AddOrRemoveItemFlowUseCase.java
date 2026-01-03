package org.radon.teleeat.integration.telegram.application.service.usecase;

import lombok.val;
import org.radon.teleeat.integration.telegram.application.port.in.SendMessagesUseCase;
import org.radon.teleeat.order.application.port.in.AddOrderItemUseCase;
import org.radon.teleeat.order.application.port.in.RemoveOrderItemUseCase;
import org.radon.teleeat.order.presentation.dto.AddOrderItemRequest;
import org.radon.teleeat.order.presentation.dto.RemoveOrderItemRequest;
import org.radon.teleeat.user.application.port.in.GetOrAddUserUseCase;
import org.springframework.stereotype.Component;

@Component
public class AddOrRemoveItemFlowUseCase {
    private final GetOrAddUserUseCase getOrAddUserUseCase;
    private final AddOrderItemUseCase addOrderItemUseCase;
    private final RemoveOrderItemUseCase removeOrderItemUseCase;
    private final SendMessagesUseCase sendMessagesUseCase;

    public AddOrRemoveItemFlowUseCase(GetOrAddUserUseCase getOrAddUserUseCase, AddOrderItemUseCase addOrderItemUseCase, RemoveOrderItemUseCase removeOrderItemUseCase, SendMessagesUseCase sendMessagesUseCase) {
        this.getOrAddUserUseCase = getOrAddUserUseCase;
        this.addOrderItemUseCase = addOrderItemUseCase;
        this.removeOrderItemUseCase = removeOrderItemUseCase;
        this.sendMessagesUseCase = sendMessagesUseCase;
    }

    public void execute(Long chatId,String data) {
        val user = getOrAddUserUseCase.getOrAdd(chatId.toString());

        val type = data.split("_")[0];

        val foodName = data.split("_")[1];


        if(type.equals("add")) {

            val foodID = data.split("_")[2];

            addOrderItemUseCase.addOrderItem(new AddOrderItemRequest(user.getId(),Long.valueOf(foodID)));

            sendMessagesUseCase.sendMessage(chatId, "You Add One " + foodName);

        }else{

            val orderItemId = data.split("_")[2];

            sendMessagesUseCase.sendMessage(chatId, "One " + foodName + " Removed from your order");

            removeOrderItemUseCase.removeOrderItem(new RemoveOrderItemRequest(Long.valueOf(orderItemId),user.getId()));

        }
    }

}
