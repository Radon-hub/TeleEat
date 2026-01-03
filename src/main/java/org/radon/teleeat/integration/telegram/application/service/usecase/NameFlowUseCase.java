package org.radon.teleeat.integration.telegram.application.service.usecase;

import lombok.val;
import org.radon.teleeat.integration.telegram.application.port.in.SendMessagesUseCase;
import org.radon.teleeat.user.application.port.in.GetOrAddUserUseCase;
import org.radon.teleeat.user.application.port.in.UpdateUserUseCase;
import org.springframework.stereotype.Component;

@Component
public class NameFlowUseCase {

    private final GetOrAddUserUseCase getOrAddUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final SendMessagesUseCase sendMessagesUseCase;


    public NameFlowUseCase(GetOrAddUserUseCase getOrAddUserUseCase, UpdateUserUseCase updateUserUseCase, SendMessagesUseCase sendMessagesUseCase) {
        this.getOrAddUserUseCase = getOrAddUserUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.sendMessagesUseCase = sendMessagesUseCase;
    }

    public void execute(Long chatId,String text) {
        val user = getOrAddUserUseCase.getOrAdd(chatId.toString());

        val userFullName = text.split(":")[1];

        updateUserUseCase.updateUser(user.getId(),userFullName,null);
        sendMessagesUseCase.sendMessage(chatId, "Your Full name is successfully updated.");
    }

}
