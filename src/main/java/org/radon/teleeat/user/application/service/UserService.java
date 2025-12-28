package org.radon.teleeat.user.application.service;

import org.radon.teleeat.user.application.port.in.AddUserUseCase;
import org.radon.teleeat.user.application.port.out.UserRepository;
import org.radon.teleeat.user.domain.User;
import org.springframework.stereotype.Service;

@Service
public class UserService implements AddUserUseCase {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void addUser(User user) {
        userRepository.addUser(user);
    }

}
