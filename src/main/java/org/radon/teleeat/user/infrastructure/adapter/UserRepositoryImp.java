package org.radon.teleeat.user.infrastructure.adapter;

import org.radon.teleeat.common.aop.exceptionHandling.UserExistException;
import org.radon.teleeat.common.aop.exceptionHandling.UserNotFound;
import org.radon.teleeat.user.application.port.out.UserRepository;
import org.radon.teleeat.user.domain.User;
import org.radon.teleeat.user.infrastructure.adapter.mapper.UserMappers;
import org.radon.teleeat.user.infrastructure.repository.UserJpaRepository;
import org.radon.teleeat.user.infrastructure.repository.entity.UserEntity;
import org.radon.teleeat.user.presentation.dto.AddUserRequest;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImp implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    public UserRepositoryImp(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }


    @Override
    public void addUser(AddUserRequest addUserRequest) {

        Optional<UserEntity> optionalUser = userJpaRepository.findUserEntitiesByTelegramIdOrPhoneNumber(addUserRequest.getTelegram_id(),addUserRequest.getPhone_number());

        if(optionalUser.isPresent()){
            throw new UserExistException();
        }

        userJpaRepository.save(UserMappers.addUserRequest(addUserRequest));

    }

    @Override
    public User getUser(Long id) {
        UserEntity user = userJpaRepository.findUserEntityById(id);
        if(user == null){
            throw new UserNotFound();
        }
        return UserMappers.userEntityToUser(user);
    }

    @Override
    public User updateUser(Long userId,String name, String phone) {
        UserEntity userEntity = userJpaRepository.findUserEntityById(userId);
        if(name != null && !name.isBlank()){
            userEntity.setFullname(name);
        }
        if(phone != null && !phone.isBlank()){
            userEntity.setPhoneNumber(phone);
        }
        return UserMappers.userEntityToUser(userJpaRepository.save(userEntity));
    }

    @Override
    public User getOrAdd(String telegram_id) {
        Optional<UserEntity> optionalUser = userJpaRepository.findUserEntityByTelegramId(telegram_id);
        return optionalUser.map(UserMappers::userEntityToUser).orElseGet(() -> UserMappers.userEntityToUser(userJpaRepository.save(UserMappers.signUpUser(telegram_id))));
    }



}
