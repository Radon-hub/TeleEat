package org.radon.teleeat.integration.telegram.infrastructure.model;

import lombok.Data;
import org.radon.teleeat.user.domain.User;

@Data
public class CallbackQuery {
    private String id;
    private User from;
    private Message message;
    private String data; // This is the callback_data
}