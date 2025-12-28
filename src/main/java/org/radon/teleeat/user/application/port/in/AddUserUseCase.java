package org.radon.teleeat.user.application.port.in;

import org.radon.teleeat.user.domain.User;

public interface AddUserUseCase {
    void addUser(User user);
}
