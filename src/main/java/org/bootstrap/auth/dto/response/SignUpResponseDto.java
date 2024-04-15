package org.bootstrap.auth.dto.response;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record SignUpResponseDto(
    Long id
) {
    public static SignUpResponseDto of(Long id) {
        return SignUpResponseDto.builder()
                .id(id)
                .build();
    }
}
