package org.radon.teleeat.user.domain;

import org.radon.teleeat.common.aop.exceptionHandling.BadArgumentsException;

import java.time.LocalDateTime;

public class User {

    private Long id;
    private String fullname;
    private String phone_number;
    private String username;
    private String password;
    private String telegram_id;
    private UserRole role = UserRole.USER;
    private LocalDateTime created_at = LocalDateTime.now();

    public User() {
    }

    private User(Builder builder) {
        this.id = builder.id;
        this.fullname = builder.fullname;
        this.phone_number = builder.phoneNumber;
        this.username = builder.username;
        this.password = builder.password;
        this.telegram_id = builder.telegramId;
        this.role = builder.role != null ? builder.role : UserRole.USER;
        this.created_at = builder.createdAt != null ? builder.createdAt : LocalDateTime.now();
    }

    public static class Factory{
        public static User addTelegramUser(String fullname, String phoneNumber, String telegramId) {
            return new User.Builder()
                    .fullname(fullname)
                    .phoneNumber(phoneNumber)
                    .telegramId(telegramId)
                    .role(UserRole.USER)
                    .build();
        }

        public static User addAdminUser(String fullname, String phone, String username, String password) {
            return new Builder()
                    .fullname(fullname)
                    .phoneNumber(phone)
                    .username(username)
                    .password(password)
                    .role(UserRole.ADMIN)
                    .build();
        }

        public static User addSupervisor(String username, String password) {
            return new Builder()
                    .username(username)
                    .password(password)
                    .role(UserRole.SUPERVISOR)
                    .build();
        }

        public static User getUser(Long id,String fullname, String phoneNumber, String username, String password,String telegramId,UserRole role,LocalDateTime createdAt) {
            return new Builder()
                    .id(id)
                    .username(username)
                    .password(password)
                    .fullname(fullname)
                    .phoneNumber(phoneNumber)
                    .telegramId(telegramId)
                    .role(role)
                    .createdAt(createdAt)
                    .build();
        }
    }

    private static class Builder {
        private Long id;
        private String fullname;
        private String phoneNumber;
        private String username;
        private String password;
        private String telegramId;
        private UserRole role;
        private LocalDateTime createdAt = LocalDateTime.now();

        public Builder id(Long id) { this.id = id; return this; }
        public Builder fullname(String fullname) { this.fullname = fullname; return this; }
        public Builder phoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; return this; }
        public Builder username(String username) { this.username = username; return this; }
        public Builder password(String password) { this.password = password; return this; }
        public Builder telegramId(String telegramId) { this.telegramId = telegramId; return this; }
        public Builder role(UserRole role) { this.role = role; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public User build() {
            if(role == UserRole.USER){
                if(telegramId.isBlank() || telegramId.length() < 5){
                    throw new BadArgumentsException("Telegram id should be at least 5 characters!");
                }
            }
            return new User(this);
        }
    }

    public void isValidForRegister(){
        if(!isNameValid()) throw new BadArgumentsException("Name must be bigger than 5 characters!");
        if(!isPhoneNumberValid()) throw new BadArgumentsException("Phone number incorrect!");
        if(!isUserNameValid()) throw new BadArgumentsException("UserName must be bigger than 5 characters!");
        if(!isPasswordValid()) throw new BadArgumentsException("Password must be bigger than 5 characters!");
    }


    private boolean isNameValid(){
        return fullname != null && fullname.length() > 5;
    }

    private boolean isPhoneNumberValid(){
        return phone_number != null && phone_number.length() == 11;
    }

    private boolean isUserNameValid(){
        return username != null && username.length() > 5;
    }

    private boolean isPasswordValid(){
        return password != null && password.length() > 5;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getFullname() {
        return fullname;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getTelegram_id() {
        return telegram_id;
    }

    public UserRole getRole() {
        return role;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }
}
