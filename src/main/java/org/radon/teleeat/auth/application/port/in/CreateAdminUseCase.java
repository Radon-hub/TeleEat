package org.radon.teleeat.auth.application.port.in;

import org.radon.teleeat.user.domain.User;

public interface CreateAdminUseCase {
    String createCall(User user);
}
