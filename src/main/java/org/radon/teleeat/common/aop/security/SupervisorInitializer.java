package org.radon.teleeat.common.aop.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.radon.teleeat.auth.application.port.in.AddSupervisorUseCase;
import org.radon.teleeat.user.domain.User;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SupervisorInitializer {

    private final AddSupervisorUseCase addSupervisorUseCase;

    @EventListener(ApplicationReadyEvent.class)
    public void initSupervisor() {

        String rawPassword = SupervisorPasswordHolder.getPassword();

        addSupervisorUseCase.addSupervisor(
                User.Factory.addSupervisor("SUPERVISOR", rawPassword)
        );

        SupervisorPasswordHolder.clearPassword();

        log.info("Supervisor persisted successfully");
    }

}
