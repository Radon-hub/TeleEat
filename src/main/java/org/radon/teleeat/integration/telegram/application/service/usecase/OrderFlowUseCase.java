package org.radon.teleeat.integration.telegram.application.service.usecase;

import lombok.val;
import org.radon.teleeat.integration.telegram.application.port.in.SendMessagesUseCase;
import org.radon.teleeat.order.application.port.in.GetUserOpenOrderUseCase;
import org.radon.teleeat.order.domain.Order;
import org.radon.teleeat.order.domain.OrderItem;
import org.radon.teleeat.user.application.port.in.GetOrAddUserUseCase;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderFlowUseCase {

    private final GetOrAddUserUseCase getOrAddUserUseCase;
    private final GetUserOpenOrderUseCase getUserOpenOrderUseCase;
    private final SendMessagesUseCase sendMessagesUseCase;

    public OrderFlowUseCase(GetOrAddUserUseCase getOrAddUserUseCase, GetUserOpenOrderUseCase getUserOpenOrderUseCase, SendMessagesUseCase sendMessagesUseCase) {
        this.getOrAddUserUseCase = getOrAddUserUseCase;
        this.getUserOpenOrderUseCase = getUserOpenOrderUseCase;
        this.sendMessagesUseCase = sendMessagesUseCase;
    }

    public void execute(Long chatId){
        val user = getOrAddUserUseCase.getOrAdd(chatId.toString());
        val openOrder = getUserOpenOrderUseCase.getOpenOrder(user.getId());
        if(openOrder == null){
            sendMessagesUseCase.sendMessage(chatId, "You don't have an order yet! ... Try adding some foods with /menu");
        }else{
            sendUserOrdersList(chatId,openOrder);
        }
    }

    private void sendUserOrdersList(Long chatId, Order order) {

        List<List<Map<String, String>>> keyboard = new ArrayList<>();
        for (OrderItem item : order.getItems()) {
            keyboard.add(List.of(Map.of(
                    "text", item.getFood().getName() + " * " + item.getCount() + " / " +  item.getFood().getPrice() + "$",
                    "callback_data", "reduce_"+item.getFood().getName()+"_"+item.getId()
            )));
        }

        Map<String, Object> body = new HashMap<>();
        body.put("chat_id", chatId);
        body.put("text", "Your Order | Total Price : "+ order.getTotalPrice() +"$ \n 1. You can remove items by click on them \n 2. You can submit your order by /submit \n 3. Or you can see menu to add more foods by /menu");
        body.put("reply_markup", Map.of("inline_keyboard", keyboard));

        sendMessagesUseCase.sendBody(body);

    }

}

