package org.radon.teleeat.user.application.port.in;

import org.radon.teleeat.user.domain.User;

public interface GetOrAddUserUseCase {
    User getOrAdd(String telegram_id);
}
