package org.radon.teleeat.user.presentation.controller;

import org.radon.teleeat.common.dto.Response;
import org.radon.teleeat.user.application.port.in.AddUserUseCase;
import org.radon.teleeat.user.infrastructure.repository.entity.UserEntity;
import org.radon.teleeat.user.presentation.dto.AddUserRequest;
import org.radon.teleeat.user.presentation.mapper.UserDtoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/user")
public class UserRestController {

    private final AddUserUseCase addUserUseCase;

    public UserRestController(AddUserUseCase addUserUseCase) {
        this.addUserUseCase = addUserUseCase;
    }


    @PostMapping("add")
    public ResponseEntity<Response<String>> addUser(@RequestBody AddUserRequest addUserRequest) {
        addUserUseCase.addUser(
                UserDtoMapper.addUserRequestToUser(addUserRequest)
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response<String>("User added successfully!"));
    }




}
