package org.radon.teleeat.integration.telegram.domain;

import lombok.Data;

@Data
public class Message {
    private int message_id;
    private Chat chat;
    private String text;
}