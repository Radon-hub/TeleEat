package org.radon.teleeat.integration.telegram.application.service;

import org.radon.teleeat.integration.telegram.application.service.usecase.*;
import org.radon.teleeat.integration.telegram.infrastructure.model.CallbackQuery;
import org.radon.teleeat.integration.telegram.infrastructure.model.Message;
import org.radon.teleeat.integration.telegram.infrastructure.model.Update;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class MessageHandler {

    private final AddOrRemoveItemFlowUseCase addOrRemoveItemFlowUseCase;
    private final AddressFlowUseCase addressFlowUseCase;
    private final MenuFlowUseCase menuFlowUseCase;
    private final NameFlowUseCase nameFlowUseCase;
    private final OrderFlowUseCase orderFlowUseCase;
    private final PhoneFlowUseCase phoneFlowUseCase;
    private final ProfileFlowUseCase profileFlowUseCase;
    private final StartFlowUseCase startFlowUseCase;
    private final SubmitFlowUseCase submitFlowUseCase;

    public MessageHandler(AddOrRemoveItemFlowUseCase addOrRemoveItemFlowUseCase, AddressFlowUseCase addressFlowUseCase, MenuFlowUseCase menuFlowUseCase, NameFlowUseCase nameFlowUseCase, OrderFlowUseCase orderFlowUseCase, PhoneFlowUseCase phoneFlowUseCase, ProfileFlowUseCase profileFlowUseCase, StartFlowUseCase startFlowUseCase, SubmitFlowUseCase submitFlowUseCase) {
        this.addOrRemoveItemFlowUseCase = addOrRemoveItemFlowUseCase;
        this.addressFlowUseCase = addressFlowUseCase;
        this.menuFlowUseCase = menuFlowUseCase;
        this.nameFlowUseCase = nameFlowUseCase;
        this.orderFlowUseCase = orderFlowUseCase;
        this.phoneFlowUseCase = phoneFlowUseCase;
        this.profileFlowUseCase = profileFlowUseCase;
        this.startFlowUseCase = startFlowUseCase;
        this.submitFlowUseCase = submitFlowUseCase;
    }


    public void handleUpdate(Update update) {
        if (update.getMessage() != null) handleMessage(update.getMessage());
        else if (update.getCallback_query() != null) handleCallback(update.getCallback_query());
    }


    private void handleMessage(Message message) {

        Long chatId = message.getChat().getId();
        String text = message.getText();


        if(text.startsWith("/")){
            if("/start".equals(text)) {
                startFlowUseCase.execute(chatId);
            }
            if ("/menu".equals(text)) {
                menuFlowUseCase.execute(chatId);
            }
            if("/order".equals(text)) {
                orderFlowUseCase.execute(chatId);
            }
            if("/submit".equals(text)) {
                submitFlowUseCase.execute(chatId);
            }
            if("/profile".equals(text)) {
                profileFlowUseCase.execute(chatId);
            }
        }

        if(text.startsWith("address:")) {
            addressFlowUseCase.execute(chatId, text);
        }

        if(text.startsWith("name:")) {
            nameFlowUseCase.execute(chatId, text);
        }

        if(text.startsWith("phone:")) {
            phoneFlowUseCase.execute(chatId, text);
        }

    }

    private void handleCallback(CallbackQuery callback) {
        Long chatId = callback.getFrom().getId();
        String data = callback.getData(); // e.g., "Pizza_1"
        addOrRemoveItemFlowUseCase.execute(chatId, data);
    }

}
