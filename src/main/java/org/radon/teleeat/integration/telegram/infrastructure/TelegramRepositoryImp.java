package org.radon.teleeat.integration.telegram.infrastructure;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.radon.teleeat.common.aop.exceptionHandling.BadArgumentsException;
import org.radon.teleeat.integration.telegram.application.port.out.TelegramRepository;
import org.radon.teleeat.integration.telegram.infrastructure.model.Message;
import org.radon.teleeat.integration.telegram.infrastructure.model.TelegramResponse;
import org.radon.teleeat.integration.telegram.infrastructure.model.Update;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class TelegramRepositoryImp implements TelegramRepository {

    private final WebClient webClient;
    private Long offset = 0L;

    public TelegramRepositoryImp() {
        String token = "8533420108:AAF_vVwbskRMZvxf7YiPerUaL09R905zV78";
        this.webClient = WebClient.builder().baseUrl(
                "https://api.telegram.org/bot" + token
        ).build();
    }

    @Override
    public TelegramResponse<List<Update>> getMessage() {

        TelegramResponse<List<Update>> result = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/getUpdates")
                        .queryParam("timeout", 30)
                        .queryParam("offset", offset + 1)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<TelegramResponse<List<Update>>>() {})
                .doOnError(ex -> log.error("Telegram polling failed", ex))
                .block();

        if (result == null || !result.isOk()) throw new BadArgumentsException("No updates found!");

        for (Update update : result.getResult()) {
            offset = update.getUpdate_id();
        }
        return result;
    }

    @Override
    public void sendMessage(Long chatId, String text) {
        TelegramResponse<Message> response = webClient.post()
                .uri("/sendMessage")
                .bodyValue(Map.of(
                        "chat_id", chatId,
                        "text", text
                ))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<TelegramResponse<Message>>() {})
                .block();

        if (response == null || !response.isOk()) {
            log.error("Failed to send message: {}", response != null ? response.getResult() : "null response");
        }
    }

    @Override
    public void sendBody(Map<String, Object> body) {
        TelegramResponse<Message> response = webClient.post()
                .uri("/sendMessage")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<TelegramResponse<Message>>() {})
                .block();

        if (response == null || !response.isOk()) {
            log.error("Failed to send message with body {}: {}", body, response != null ? response.getResult() : "null response");
        }
    }
}
