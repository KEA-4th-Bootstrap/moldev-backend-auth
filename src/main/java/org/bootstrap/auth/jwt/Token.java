package org.bootstrap.auth.jwt;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Token {

    private String accessToken;
    private String refreshToken;

    public static Token of(String accessToken, String refreshToken) {
        return Token.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
