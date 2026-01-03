package org.radon.teleeat.integration.telegram.application.port.in;

import org.radon.teleeat.integration.telegram.infrastructure.model.TelegramResponse;
import org.radon.teleeat.integration.telegram.infrastructure.model.Update;

import java.util.List;

public interface GetMessagesUseCase {
    TelegramResponse<List<Update>> getMessage();
}
