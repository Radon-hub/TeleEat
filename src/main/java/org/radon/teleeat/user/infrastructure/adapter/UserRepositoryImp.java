package org.radon.teleeat.user.infrastructure.adapter;

import org.radon.teleeat.common.aop.exceptionHandling.UserExistException;
import org.radon.teleeat.user.application.port.out.UserRepository;
import org.radon.teleeat.user.domain.User;
import org.radon.teleeat.user.infrastructure.adapter.mapper.UserMappers;
import org.radon.teleeat.user.infrastructure.repository.UserJpaRepository;
import org.radon.teleeat.user.infrastructure.repository.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImp implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    public UserRepositoryImp(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }


    @Override
    public void addUser(User user) {

        Optional<UserEntity> optionalUser = userJpaRepository.findUserEntitiesByTelegramIdOrPhoneNumber(user.getTelegram_id(),user.getPhone_number());

        if(optionalUser.isPresent()){
            throw new UserExistException();
        }

        userJpaRepository.save(UserMappers.userToUserEntity(user));

    }

    @Override
    public User getUser(Long id) {
        return UserMappers.userEntityToUser(userJpaRepository.findUserEntityById((id)));
    }
}
