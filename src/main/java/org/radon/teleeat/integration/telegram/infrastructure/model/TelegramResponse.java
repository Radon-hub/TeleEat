package org.radon.teleeat.integration.telegram.infrastructure.model;

import lombok.Data;

import java.util.List;

@Data
public class TelegramResponse<T> {
    private boolean ok;
    private T result;
}
