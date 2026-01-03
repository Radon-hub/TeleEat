package org.radon.teleeat.integration.telegram.application.service;

import lombok.val;
import org.radon.teleeat.integration.telegram.application.port.in.GetMessagesUseCase;
import org.radon.teleeat.integration.telegram.application.port.in.SendMessagesUseCase;
import org.radon.teleeat.integration.telegram.infrastructure.model.Update;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PollingScheduler {

    private final GetMessagesUseCase getMessagesUseCase;
    private final MessageHandler messageHandler;

    public PollingScheduler(GetMessagesUseCase getMessagesUseCase, MessageHandler messageHandler) {
        this.getMessagesUseCase = getMessagesUseCase;
        this.messageHandler = messageHandler;
    }

    @Scheduled(fixedDelay = 1000)
    public void polling() {
        val response = getMessagesUseCase.getMessage();
        for (Update update : response.getResult()) {
            messageHandler.handleUpdate(update);
        }
    }

}
