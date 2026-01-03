package org.radon.teleeat.integration.telegram.application.service.usecase;

import lombok.val;
import org.radon.teleeat.integration.telegram.application.port.in.SendMessagesUseCase;
import org.radon.teleeat.order.application.port.in.GetUserOpenOrderUseCase;
import org.radon.teleeat.order.application.port.in.UpdateOrderStatusUseCase;
import org.radon.teleeat.order.domain.Order;
import org.radon.teleeat.order.domain.OrderStatus;
import org.radon.teleeat.user.application.port.in.GetOrAddUserUseCase;
import org.springframework.stereotype.Component;

@Component
public class SubmitFlowUseCase {

    private final GetOrAddUserUseCase getOrAddUserUseCase;
    private final GetUserOpenOrderUseCase getUserOpenOrderUseCase;
    private final SendMessagesUseCase sendMessagesUseCase;
    private final UpdateOrderStatusUseCase updateOrderStatusUseCase;

    public SubmitFlowUseCase(GetOrAddUserUseCase getOrAddUserUseCase, GetUserOpenOrderUseCase getUserOpenOrderUseCase, SendMessagesUseCase sendMessagesUseCase, UpdateOrderStatusUseCase updateOrderStatusUseCase) {
        this.getOrAddUserUseCase = getOrAddUserUseCase;
        this.getUserOpenOrderUseCase = getUserOpenOrderUseCase;
        this.sendMessagesUseCase = sendMessagesUseCase;
        this.updateOrderStatusUseCase = updateOrderStatusUseCase;
    }

    public void execute(Long chatId) {
        val user = getOrAddUserUseCase.getOrAdd(chatId.toString());
        val openOrder = getUserOpenOrderUseCase.getOpenOrder(user.getId());

        if(openOrder == null || openOrder.getItems() == null || openOrder.getItems().isEmpty()){
            sendMessagesUseCase.sendMessage(chatId, "You have to add at least one item to submit your order! \n Try adding some foods with /menu");
        }else{

            boolean isFullnameMissing = user.getFullname() == null || user.getFullname().isBlank();
            boolean isPhoneMissing = user.getPhone_number() == null || user.getPhone_number().isBlank();
            boolean isAddressMissing = openOrder.getAddress() == null || openOrder.getAddress().isBlank();

            StringBuilder msgBuilder = new StringBuilder();

            if (isFullnameMissing || isPhoneMissing) {

                msgBuilder.append("There are some issues in your profile:\n\n");

                if (isFullnameMissing) {
                    msgBuilder.append("- You didn’t specify your full name\n");
                }
                if (isPhoneMissing) {
                    msgBuilder.append("- You didn’t specify your phone number\n");
                }

                msgBuilder.append("\nYou can edit these under /profile");

                sendMessagesUseCase.sendMessage(chatId, msgBuilder.toString());

            }else{

                if(isAddressMissing){
                    sendMessagesUseCase.sendMessage(chatId, "Please enter your address\n\n * Example = (address:Tehran - Gandi St - 21th Ave ...) ");
                }else{
                    submitOrderAndDeliver(chatId,openOrder);
                }


            }

        }
    }


    private void submitOrderAndDeliver(Long chatId, Order openOrder){

//          TODO : WE CAN HAVE A PAYMENT HERE
//          updateOrderStatusUseCase.updateOrderStatus(openOrder.getId(), OrderStatus.PENDING_PAYMENT);

        updateOrderStatusUseCase.updateOrderStatus(openOrder.getId(), OrderStatus.DELIVERY);

        sendMessagesUseCase.sendMessage(chatId, "Thank you for submitting your order! \n\n Your food is on delivery now. \n\n Enjoy your food with TeleEat ;-) \n\n Bye...");
    }
}
