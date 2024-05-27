package org.bootstrap.auth.dto.response;

import lombok.Builder;

@Builder
public record ReissueResponseDto(
        String accessToken
) {
    public static ReissueResponseDto of(String accessToken) {
        return ReissueResponseDto.builder()
                .accessToken(accessToken)
                .build();
    }
}
