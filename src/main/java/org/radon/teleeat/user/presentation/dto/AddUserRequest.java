package org.radon.teleeat.user.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.radon.teleeat.user.domain.UserRole;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddUserRequest {
    private String fullname;
    private String phone_number;
    private String telegram_id;
}
