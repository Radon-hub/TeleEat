package org.radon.teleeat.auth.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminSignUpRequest {
    private String fullName;
    private String phoneNumber;
    private String password;
    private String userName;
}
