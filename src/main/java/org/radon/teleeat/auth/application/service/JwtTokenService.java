package org.radon.teleeat.auth.application.service;

import org.radon.teleeat.auth.application.port.in.AdminLoginUseCase;
import org.radon.teleeat.auth.application.port.in.RefreshTokenUseCase;
import org.radon.teleeat.auth.application.port.out.AuthRepository;
import org.radon.teleeat.auth.domain.AuthModel;
import org.radon.teleeat.auth.presentation.dto.AdminLoginRequest;
import org.radon.teleeat.auth.presentation.dto.RefreshTokenRequest;
import org.radon.teleeat.common.aop.exceptionHandling.InvalidTokenException;
import org.radon.teleeat.common.aop.security.JWTUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenService implements RefreshTokenUseCase, AdminLoginUseCase {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final AuthRepository authRepository;

    public JwtTokenService(AuthenticationManager authenticationManager, JWTUtil jwtUtil, AuthRepository authRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.authRepository = authRepository;
    }

    @Override
    public AuthModel login(AdminLoginRequest adminLoginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(adminLoginRequest.getUserName(),adminLoginRequest.getPassword())
        );

        UserDetails user = authRepository.loadUserByUsername(adminLoginRequest.getUserName());

        String token = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);

        return new AuthModel.Builder().token(token).refreshToken(refreshToken).build();
    }

    @Override
    public AuthModel refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String username = jwtUtil.extractUsername(refreshTokenRequest.getRefreshToken());

        if(username == null) throw new InvalidTokenException();

        UserDetails user = authRepository.loadUserByUsername(username);

        if(!jwtUtil.isTokenValid(refreshTokenRequest.getRefreshToken(), user)) throw new InvalidTokenException();

        String newAccessToken = jwtUtil.generateAccessToken(user);
        String newRefreshToken = jwtUtil.generateRefreshToken(user);

        return new AuthModel.Builder().token(newAccessToken).refreshToken(newRefreshToken).build();
    }
}
