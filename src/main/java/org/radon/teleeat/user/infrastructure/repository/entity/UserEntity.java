package org.radon.teleeat.user.infrastructure.repository.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.radon.teleeat.user.domain.UserRole;

import java.time.LocalDateTime;

@Table(name = "users_table")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullname;
    @Column(name = "phone_number", unique = true)
    private String phoneNumber;
    private String username;
    private String password;
    @Column(name = "telegram_id", unique = true)
    private String telegramId;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public UserEntity(String fullname, String phoneNumber, String username,String password, String telegramId, UserRole role) {
        this.fullname = fullname;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
        this.telegramId = telegramId;
        this.role = role;
    }
}
