package org.radon.teleeat.auth.application.port.out;

import org.radon.teleeat.user.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthRepository extends UserDetailsService {
    String createAdmin(User user);
    User addSupervisor(User user);
    User getAdminWithUserName(String userName);
}
