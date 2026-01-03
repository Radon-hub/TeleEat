package org.radon.teleeat.user.presentation.mapper;

import org.radon.teleeat.user.domain.User;
import org.radon.teleeat.user.domain.UserRole;
import org.radon.teleeat.user.presentation.dto.AddUserRequest;
import org.radon.teleeat.user.presentation.dto.UserResponse;

import java.time.LocalDateTime;

public class UserDtoMapper {

    public static UserResponse fromUserToResponse(User user){
        return new UserResponse(
                user.getId(),
                user.getFullname(),
                user.getPhone_number(),
                user.getTelegram_id()
        );
    }

    public static User addUserRequestToUser(AddUserRequest addUserRequest) {
        return User.Factory.addTelegramUser(
                addUserRequest.getFullname(),
                addUserRequest.getPhone_number(),
                addUserRequest.getTelegram_id()
        );
    }
}
