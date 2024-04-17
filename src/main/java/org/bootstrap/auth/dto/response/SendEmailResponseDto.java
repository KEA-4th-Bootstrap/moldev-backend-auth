package org.bootstrap.auth.dto.response;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record SendEmailResponseDto(
    boolean isSuccess
) {
    public static SendEmailResponseDto of(boolean isSuccess) {
        return SendEmailResponseDto.builder()
                .isSuccess(isSuccess)
                .build();
    }
}
