package org.radon.teleeat.user.infrastructure.adapter.mapper;

import org.radon.teleeat.order.domain.Order;
import org.radon.teleeat.order.infrastructure.adapter.mapper.OrderMappers;
import org.radon.teleeat.user.domain.User;
import org.radon.teleeat.user.domain.UserRole;
import org.radon.teleeat.user.infrastructure.repository.entity.UserEntity;
import org.radon.teleeat.user.presentation.dto.AddUserRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserMappers {


    public static UserEntity signUpUser(String telegram_id){
        return new UserEntity(
                null,
                null,
                null,
                null,
                telegram_id,
                UserRole.USER,
                new ArrayList<>()
        );
    }

    public static UserEntity addUserRequest(AddUserRequest addUserRequest){
        return new UserEntity(
                addUserRequest.getFullname(),
                addUserRequest.getPhone_number(),
                null,
                null,
                addUserRequest.getTelegram_id(),
                UserRole.USER,
                new ArrayList<>()
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
                userEntity.getCreatedAt(),
                Collections.emptyList()
        );
    }

    public static User userEntityToUserWithOrders(UserEntity userEntity) {
        return User.Factory.getUser(
                userEntity.getId(),
                userEntity.getFullname(),
                userEntity.getPhoneNumber(),
                userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.getTelegramId(),
                userEntity.getRole(),
                userEntity.getCreatedAt(),
                Collections.emptyList()
        );
    }

    public static UserEntity userToUserEntity(User user) {
        return new UserEntity(
                user.getId(),
                user.getFullname(),
                user.getPhone_number(),
                user.getUsername(),
                user.getPassword(),
                user.getTelegram_id(),
                user.getRole(),
                user.getOrders().stream().map(OrderMappers::fromOrderToOrderEntity).toList(),
                user.getCreated_at()
        );
    }
}
