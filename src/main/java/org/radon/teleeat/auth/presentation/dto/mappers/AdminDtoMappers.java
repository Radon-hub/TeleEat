package org.radon.teleeat.auth.presentation.dto.mappers;

import org.radon.teleeat.auth.domain.AuthModel;
import org.radon.teleeat.auth.presentation.dto.AdminLoginRequest;
import org.radon.teleeat.auth.presentation.dto.AdminSignUpRequest;
import org.radon.teleeat.auth.presentation.dto.AuthResponse;
import org.radon.teleeat.user.domain.User;

public class AdminDtoMappers {

    public static User adminLoginRequestToUser(AdminSignUpRequest addUserRequest) {
        return User.Factory.addAdminUser(
                addUserRequest.getFullName(),
                addUserRequest.getPhoneNumber(),
                addUserRequest.getUserName(),
                addUserRequest.getPassword()
        );
    }

    public static AuthResponse authModelToAuthResponse(AuthModel authModel){
        return new AuthResponse(
                authModel.getToken(),
                authModel.getRefreshToken()
        );
    }


}
