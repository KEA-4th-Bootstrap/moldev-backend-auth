package org.bootstrap.auth.dto.response;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record LoginResponseDto (
    Long memberId,
    String accessToken,
    String refreshToken
) {
    public static LoginResponseDto create(Long memberId, String accessToken, String refreshToken) {
        return LoginResponseDto.builder()
                .memberId(memberId)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
