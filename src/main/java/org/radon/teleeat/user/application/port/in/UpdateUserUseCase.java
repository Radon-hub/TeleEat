package org.radon.teleeat.user.application.port.in;

import org.radon.teleeat.user.domain.User;

public interface UpdateUserUseCase {
    User updateUser(Long userId,String name, String phone);
}
