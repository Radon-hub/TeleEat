package org.radon.teleeat.user.infrastructure.repository;

import org.radon.teleeat.user.domain.User;
import org.radon.teleeat.user.domain.UserRole;
import org.radon.teleeat.user.infrastructure.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    @Query("""
    select u from UserEntity u
    left join fetch u.orders
    where u.username = :username
    """)
    Optional<UserEntity> findUserEntitiesByUsername(String username);

    Optional<UserEntity> findUserEntitiesByTelegramId(String telegramId);

    Optional<UserEntity> findUserEntitiesByTelegramIdOrPhoneNumber(String telegramId, String phoneNumber);

    @Query("""
    select u from UserEntity u
    left join fetch u.orders
    where u.telegramId = :telegramId
    """)
    Optional<UserEntity> findUserEntityByTelegramId(String telegramId);

    UserEntity findUserEntityById(Long id);

    UserEntity findUserEntityByPhoneNumberAndPassword(String phoneNumber, String password);

    Optional<UserEntity> findUserEntityByPhoneNumber(String phoneNumber);

    UserEntity findUserEntityByRole(UserRole role);
}
