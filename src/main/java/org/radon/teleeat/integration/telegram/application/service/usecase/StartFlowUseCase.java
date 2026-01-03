package org.radon.teleeat.integration.telegram.application.service.usecase;

import lombok.val;
import org.radon.teleeat.integration.telegram.application.port.in.SendMessagesUseCase;
import org.radon.teleeat.user.application.port.in.GetOrAddUserUseCase;
import org.springframework.stereotype.Component;

@Component
public class StartFlowUseCase {

    private final SendMessagesUseCase sendMessagesUseCase;
    private final GetOrAddUserUseCase getOrAddUserUseCase;

    public StartFlowUseCase(SendMessagesUseCase sendMessagesUseCase, GetOrAddUserUseCase getOrAddUserUseCase) {
        this.sendMessagesUseCase = sendMessagesUseCase;
        this.getOrAddUserUseCase = getOrAddUserUseCase;
    }

    public void execute(Long chatId){
        val user = getOrAddUserUseCase.getOrAdd(chatId.toString());
        String greetings;
        if(user.getFullname() != null){
            greetings = "Hello " + user.getFullname() + ", Welcome back :-)";
        }else{
            greetings = "Hello and welcome to TeleEat";
        }
        sendMessagesUseCase.sendMessage(chatId, greetings+"\n\nDo you want to order foods ? ... \n  1. Try enter /menu to see our dishes! \n  2. Try enter /order to see your order and submit");
    }


}
