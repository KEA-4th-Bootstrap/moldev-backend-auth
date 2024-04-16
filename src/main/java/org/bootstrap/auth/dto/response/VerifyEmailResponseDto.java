package org.bootstrap.auth.dto.response;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record VerifyEmailResponseDto(
    Boolean isVerified
) {
    public static VerifyEmailResponseDto of(Boolean isVerified) {
        return VerifyEmailResponseDto.builder()
                .isVerified(isVerified)
                .build();
    }
}
