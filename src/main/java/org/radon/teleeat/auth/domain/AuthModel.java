package org.radon.teleeat.auth.domain;

public class AuthModel {

    private String token;
    private String refreshToken;

    private AuthModel(Builder builder) {
        this.refreshToken = builder.refreshToken;
        this.token = builder.token;
    }

    public static class Builder{

        private String token;
        private String refreshToken;

        public Builder token(String token){
            this.token = token;
            return this;
        }
        public Builder refreshToken(String refreshToken){
            this.refreshToken = refreshToken;
            return this;
        }
        public AuthModel build(){
            return new AuthModel(this);
        }

    }

    public String getToken() {
        return token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

}
