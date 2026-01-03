package org.radon.teleeat.integration.telegram.application.port.in;

import java.util.Map;

public interface SendMessagesUseCase {
    void sendMessage(Long chatId, String text);
    void sendBody(Map<String, Object> body);
}
