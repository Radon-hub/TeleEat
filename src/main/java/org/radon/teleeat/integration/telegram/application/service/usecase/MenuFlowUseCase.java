package org.radon.teleeat.integration.telegram.application.service.usecase;

import org.radon.teleeat.food.application.port.in.GetFoodsPagination;
import org.radon.teleeat.food.domain.Food;
import org.radon.teleeat.integration.telegram.application.port.in.SendMessagesUseCase;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MenuFlowUseCase {

    private final SendMessagesUseCase sendMessagesUseCase;
    private final GetFoodsPagination getFoodsPagination;

    public MenuFlowUseCase(SendMessagesUseCase sendMessagesUseCase, GetFoodsPagination getFoodsPagination) {
        this.sendMessagesUseCase = sendMessagesUseCase;
        this.getFoodsPagination = getFoodsPagination;
    }

    public void execute(Long chatId) {
        sendFoodList(chatId,getFoodsPagination.getFoods(Pageable.ofSize(50)).stream().toList());
    }

    private void sendFoodList(Long chatId, List<Food> foodList) {

        List<List<Map<String, String>>> keyboard = new ArrayList<>();
        for (Food food : foodList) {
            keyboard.add(List.of(Map.of(
                    "text", food.getName() + " | " +  food.getPrice().toString()+"$",
                    "callback_data", "add_"+food.getName()+"_"+food.getId().toString()
            )));
        }

        Map<String, Object> body = new HashMap<>();
        body.put("chat_id", chatId);
        body.put("text", "Menu : \n 1. To order, just click on your favorite food \n 2. To see your order, Try /order");
        body.put("reply_markup", Map.of("inline_keyboard", keyboard));

        sendMessagesUseCase.sendBody(body);
    }

}
