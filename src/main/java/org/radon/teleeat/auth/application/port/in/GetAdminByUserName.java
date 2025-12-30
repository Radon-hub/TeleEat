package org.radon.teleeat.auth.application.port.in;


import org.radon.teleeat.user.domain.User;

public interface GetAdminByUserName {
    User getByUserName(String username);
}
