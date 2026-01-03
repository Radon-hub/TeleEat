package org.radon.teleeat.integration.telegram.infrastructure.model;

import lombok.Data;

@Data
public class Update {
    private Long update_id;
    private Message message;
    private CallbackQuery callback_query; // new
}