package org.radon.teleeat.user.infrastructure.adapter.mapper;

import org.radon.teleeat.user.domain.User;
import org.radon.teleeat.user.infrastructure.repository.entity.UserEntity;

public class UserMappers {


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
