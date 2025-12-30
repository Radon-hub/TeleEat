package org.radon.teleeat.auth.application.port.in;

import org.radon.teleeat.auth.domain.AuthModel;
import org.radon.teleeat.auth.presentation.dto.AdminLoginRequest;
import org.radon.teleeat.auth.presentation.dto.AuthResponse;

public interface AdminLoginUseCase {
    AuthModel login(AdminLoginRequest adminLoginRequest);
}
