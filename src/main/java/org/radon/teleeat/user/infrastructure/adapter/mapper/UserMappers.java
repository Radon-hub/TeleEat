package org.radon.teleeat.user.infrastructure.adapter.mapper;

import org.radon.teleeat.user.domain.User;
import org.radon.teleeat.user.domain.UserRole;
import org.radon.teleeat.user.infrastructure.repository.entity.UserEntity;
import org.radon.teleeat.user.presentation.dto.AddUserRequest;

import java.time.LocalDateTime;

public class UserMappers {


    public static UserEntity signUpUser(String telegram_id){
        return new UserEntity(
                null,
                null,
                null,
                null,
                telegram_id,
                UserRole.USER
        );
    }

    public static UserEntity addUserRequest(AddUserRequest addUserRequest){
        return new UserEntity(
                addUserRequest.getFullname(),
                addUserRequest.getPhone_number(),
                null,
                null,
                addUserRequest.getTelegram_id(),
                UserRole.USER
        );
    }

    public static User userEntityToUser(UserEntity userEntity) {
        return User.Factory.getUser(
                userEntity.getId(),
                userEntity.getFullname(),
                userEntity.getPhoneNumber(),
                userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.getTelegramId(),
                userEntity.getRole(),
                userEntity.getCreatedAt()
        );
    }

    public static UserEntity userToUserEntity(User user) {
        return new UserEntity(
                user.getFullname(),
                user.getPhone_number(),
                user.getUsername(),
                user.getPassword(),
                user.getTelegram_id(),
                user.getRole()
        );
    }
}
