package org.radon.teleeat.user.application.service;

import org.radon.teleeat.user.application.port.in.AddUserUseCase;
import org.radon.teleeat.user.application.port.in.GetOrAddUserUseCase;
import org.radon.teleeat.user.application.port.in.UpdateUserUseCase;
import org.radon.teleeat.user.application.port.out.UserRepository;
import org.radon.teleeat.user.domain.User;
import org.radon.teleeat.user.presentation.dto.AddUserRequest;
import org.springframework.stereotype.Service;

@Service
public class UserService implements AddUserUseCase, GetOrAddUserUseCase, UpdateUserUseCase {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void addUser(AddUserRequest addUserRequest) {
        userRepository.addUser(addUserRequest);
    }


    @Override
    public User getOrAdd(String telegram_id) {
        return userRepository.getOrAdd(telegram_id);
    }


    @Override
    public User updateUser(Long userId,String name, String phone) {
        return  userRepository.updateUser(userId,name, phone);
    }
}
