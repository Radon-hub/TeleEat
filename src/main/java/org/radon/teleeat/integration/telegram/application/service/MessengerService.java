package org.radon.teleeat.integration.telegram.application.service;

import lombok.val;
import org.radon.teleeat.integration.telegram.application.port.in.GetMessagesUseCase;
import org.radon.teleeat.integration.telegram.application.port.in.SendMessagesUseCase;
import org.radon.teleeat.integration.telegram.application.port.out.TelegramRepository;
import org.radon.teleeat.integration.telegram.infrastructure.model.TelegramResponse;
import org.radon.teleeat.integration.telegram.infrastructure.model.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MessengerService implements GetMessagesUseCase, SendMessagesUseCase {

    private final TelegramRepository telegramRepository;


    public MessengerService(TelegramRepository telegramRepository) {
        this.telegramRepository = telegramRepository;
    }

    @Override
    public TelegramResponse<List<Update>> getMessage() {
        return telegramRepository.getMessage();
    }

    @Override
    public void sendMessage(Long chatId, String text) {
        telegramRepository.sendMessage(chatId, text);
    }

    @Override
    public void sendBody(Map<String, Object> body) {
        telegramRepository.sendBody(body);
    }
}
