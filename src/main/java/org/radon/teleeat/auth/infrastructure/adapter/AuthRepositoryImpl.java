package org.radon.teleeat.auth.infrastructure.adapter;


import jakarta.transaction.Transactional;
import lombok.val;
import org.radon.teleeat.auth.application.port.out.AuthRepository;
import org.radon.teleeat.common.aop.exceptionHandling.UserExistException;
import org.radon.teleeat.common.aop.exceptionHandling.UserNotFound;
import org.radon.teleeat.user.domain.User;
import org.radon.teleeat.user.infrastructure.adapter.mapper.UserMappers;
import org.radon.teleeat.user.infrastructure.repository.UserJpaRepository;
import org.radon.teleeat.user.infrastructure.repository.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AuthRepositoryImpl implements AuthRepository {

    private final UserJpaRepository userJpaRepository;
    private final PasswordEncoder passwordEncoder;


    public AuthRepositoryImpl(UserJpaRepository userJpaRepository, PasswordEncoder passwordEncoder) {
        this.userJpaRepository = userJpaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        val user = getAdminWithUserName(username);

        if(user == null) throw new UsernameNotFoundException(username);

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().toString())
                .disabled(false)
                .build();
    }

    @Transactional
    @Override
    public User addSupervisor(User user) {

        UserEntity userEntity = userJpaRepository.findUserEntityByRole(user.getRole());

        if(userEntity != null){
            userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        }else{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userJpaRepository.save(UserMappers.userToUserEntity(user));
        }

        return user;
    }

    @Override
    public String createAdmin(User user) {
        user.isValidForRegister();

        Optional<UserEntity> userEntity = userJpaRepository.findUserEntityByPhoneNumber(user.getPhone_number());

        if(userEntity.isPresent()){
            throw new UserExistException();
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userJpaRepository.save(UserMappers.userToUserEntity(user));

        return "Admin registered successfully!";
    }

    public User getAdminWithUserName(String userName) {

        Optional<UserEntity> userEntity = userJpaRepository.findUserEntitiesByUsername(userName);

        if(userEntity.isEmpty()){
            throw new UserNotFound();
        }

        return UserMappers.userEntityToUser(userEntity.get());
    }

}
