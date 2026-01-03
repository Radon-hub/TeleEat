package org.radon.teleeat.integration.telegram.infrastructure.model;

import lombok.Data;

@Data
public class Message {
    private int message_id;
    private Chat chat;
    private String text;
}