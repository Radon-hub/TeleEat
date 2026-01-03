package org.radon.teleeat.integration.telegram.application.service.usecase;

import lombok.val;
import org.radon.teleeat.integration.telegram.application.port.in.SendMessagesUseCase;
import org.radon.teleeat.user.application.port.in.GetOrAddUserUseCase;
import org.springframework.stereotype.Component;

@Component
public class ProfileFlowUseCase {

    private final GetOrAddUserUseCase getOrAddUserUseCase;
    private final SendMessagesUseCase sendMessagesUseCase;

    public ProfileFlowUseCase(GetOrAddUserUseCase getOrAddUserUseCase, SendMessagesUseCase sendMessagesUseCase) {
        this.getOrAddUserUseCase = getOrAddUserUseCase;
        this.sendMessagesUseCase = sendMessagesUseCase;
    }

    public void execute(Long chatId) {
        val user = getOrAddUserUseCase.getOrAdd(chatId.toString());

        boolean isFullnameMissing = user.getFullname() == null || user.getFullname().isBlank();
        boolean isPhoneMissing = user.getPhone_number() == null || user.getPhone_number().isBlank();

        StringBuilder msgBuilder = new StringBuilder();

        if (isFullnameMissing || isPhoneMissing) {

            msgBuilder.append("There are some issues in your profile:\n\n");

            if (isFullnameMissing) {
                msgBuilder.append("- Full name is missing,Try set it up -> name:Jake william\n");
            }
            if (isPhoneMissing) {
                msgBuilder.append("- Phone number is missing,Try set it up -> phone:09300000000\n");
            }

            sendMessagesUseCase.sendMessage(chatId, msgBuilder.toString());

        }else{
            sendMessagesUseCase.sendMessage(chatId, "Your profile is up to date.");
        }
    }

}
