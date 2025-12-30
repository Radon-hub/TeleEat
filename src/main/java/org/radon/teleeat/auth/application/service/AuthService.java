package org.radon.teleeat.auth.application.service;


import org.radon.teleeat.auth.application.port.in.*;
import org.radon.teleeat.auth.application.port.out.AuthRepository;
import org.radon.teleeat.user.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements AddSupervisorUseCase,CreateAdminUseCase, GetAdminByUserName, LoadAdminByUserNameUseCase {

    private final AuthRepository authRepository;


    public AuthService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Override
    public String createCall(User user) {
        return authRepository.createAdmin(user);
    }

    @Override
    public UserDetails loadAdminByUsername(String username) {
        return authRepository.loadUserByUsername(username);
    }

    @Override
    public User addSupervisor(User user) {
        return authRepository.addSupervisor(user);
    }

    @Override
    public User getByUserName(String username) {
        return authRepository.getAdminWithUserName(username);
    }

}
