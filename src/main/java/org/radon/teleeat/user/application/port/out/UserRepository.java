package org.radon.teleeat.user.application.port.out;

import org.radon.teleeat.user.domain.User;
import org.radon.teleeat.user.presentation.dto.AddUserRequest;

public interface UserRepository {
    void addUser(AddUserRequest addUserRequest);
    User getUser(Long id);
    User updateUser(Long userId,String name, String phone);
    User getOrAdd(String telegram_id);
}
