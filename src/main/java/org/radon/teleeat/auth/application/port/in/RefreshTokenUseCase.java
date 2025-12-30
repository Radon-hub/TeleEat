package org.radon.teleeat.auth.application.port.in;

import org.radon.teleeat.auth.domain.AuthModel;
import org.radon.teleeat.auth.presentation.dto.AuthResponse;
import org.radon.teleeat.auth.presentation.dto.RefreshTokenRequest;

public interface RefreshTokenUseCase {
    AuthModel refreshToken(RefreshTokenRequest refreshTokenRequest);
}
