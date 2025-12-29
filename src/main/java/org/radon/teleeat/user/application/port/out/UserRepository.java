package org.radon.teleeat.user.application.port.out;

import org.radon.teleeat.user.domain.User;

public interface UserRepository {
    void addUser(User user);
    User getUser(Long id);
}
