package org.radon.teleeat.auth.presentation.controller;

import lombok.val;
import org.radon.teleeat.auth.application.port.in.AdminLoginUseCase;
import org.radon.teleeat.auth.application.port.in.CreateAdminUseCase;
import org.radon.teleeat.auth.application.port.in.RefreshTokenUseCase;
import org.radon.teleeat.auth.presentation.dto.AdminLoginRequest;
import org.radon.teleeat.auth.presentation.dto.AdminSignUpRequest;
import org.radon.teleeat.auth.presentation.dto.AuthResponse;
import org.radon.teleeat.auth.presentation.dto.RefreshTokenRequest;
import org.radon.teleeat.auth.presentation.dto.mappers.AdminDtoMappers;
import org.radon.teleeat.common.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth/admin")
public class AuthController {

    private final CreateAdminUseCase createAdminUseCase;
    private final AdminLoginUseCase adminLoginUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;

    public AuthController(CreateAdminUseCase createAdminUseCase, AdminLoginUseCase adminLoginUseCase, RefreshTokenUseCase refreshTokenUseCase) {
        this.createAdminUseCase = createAdminUseCase;
        this.adminLoginUseCase = adminLoginUseCase;
        this.refreshTokenUseCase = refreshTokenUseCase;
    }


    @PreAuthorize("hasRole('SUPERVISOR')")
    @PostMapping("signup")
    public ResponseEntity<Response<String>> createVendor(@RequestBody AdminSignUpRequest vendorRequest) {
        return ResponseEntity.ok().body(
                new Response<>(
                        createAdminUseCase.createCall(AdminDtoMappers.adminLoginRequestToUser(vendorRequest))
                )
        );
    }

    @PostMapping("refresh-token")
    public ResponseEntity<Response<AuthResponse>> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        val response = refreshTokenUseCase.refreshToken(refreshTokenRequest);
        return ResponseEntity.ok(new Response<>(AdminDtoMappers.authModelToAuthResponse(response)));
    }

    @PostMapping("login")
    public ResponseEntity<Response<AuthResponse>> login(@RequestBody AdminLoginRequest adminLoginRequest){
        val response = adminLoginUseCase.login(adminLoginRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response<>(AdminDtoMappers.authModelToAuthResponse(response)));
    }



}
