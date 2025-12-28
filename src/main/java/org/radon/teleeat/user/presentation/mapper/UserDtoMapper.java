package org.radon.teleeat.user.presentation.mapper;

import org.radon.teleeat.user.domain.User;
import org.radon.teleeat.user.domain.UserRole;
import org.radon.teleeat.user.presentation.dto.AddUserRequest;

import java.time.LocalDateTime;

public class UserDtoMapper {

    public static User addUserRequestToUser(AddUserRequest addUserRequest) {
        return User.Factory.addTelegramUser(
                addUserRequest.getFullname(),
                addUserRequest.getPhone_number(),
                addUserRequest.getTelegram_id()
        );
    }
}
