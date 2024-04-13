package org.bootstrap.auth.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import org.bootstrap.auth.jwt.Token;

@Builder(access = AccessLevel.PRIVATE)
public record LoginResponseDto (
    Long memberId,
    String accessToken,
    String refreshToken
) {
    public static LoginResponseDto of(Long memberId, Token token) {
        return LoginResponseDto.builder()
                .memberId(memberId)
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .build();
    }
}
