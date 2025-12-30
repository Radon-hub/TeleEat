package org.radon.teleeat.integration.telegram.domain;

import lombok.Data;

import java.util.List;

@Data
public class TelegramResponse {
    private boolean ok;
    private List<Update> result;
}
