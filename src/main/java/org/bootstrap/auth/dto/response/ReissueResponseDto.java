package org.bootstrap.auth.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public record ReissueResponseDto(
        String accessToken,
        String refreshToken
) {
    public static ReissueResponseDto of(String accessToken, String refreshToken) {
        return ReissueResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
