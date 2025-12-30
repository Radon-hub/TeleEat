package org.radon.teleeat.auth.application.port.in;

import org.radon.teleeat.user.domain.User;

public interface AddSupervisorUseCase {
    User addSupervisor(User user);
}
