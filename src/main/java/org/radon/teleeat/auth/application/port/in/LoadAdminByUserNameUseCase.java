package org.radon.teleeat.auth.application.port.in;

import org.springframework.security.core.userdetails.UserDetails;

public interface LoadAdminByUserNameUseCase {
    UserDetails loadAdminByUsername(String username);
}
