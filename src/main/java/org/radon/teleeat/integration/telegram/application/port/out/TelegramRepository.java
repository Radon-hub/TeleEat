package org.radon.teleeat.integration.telegram.application.port.out;


import org.radon.teleeat.integration.telegram.infrastructure.model.TelegramResponse;
import org.radon.teleeat.integration.telegram.infrastructure.model.Update;

import java.util.List;
import java.util.Map;

public interface TelegramRepository {
    TelegramResponse<List<Update>> getMessage();
    void sendMessage(Long chatId, String text);
    void sendBody(Map<String, Object> body);
}
