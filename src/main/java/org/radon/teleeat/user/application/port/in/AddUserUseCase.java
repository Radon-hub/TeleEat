package org.radon.teleeat.user.application.port.in;

import org.radon.teleeat.user.domain.User;
import org.radon.teleeat.user.presentation.dto.AddUserRequest;

public interface AddUserUseCase {
    void addUser(AddUserRequest addUserRequest);
}
