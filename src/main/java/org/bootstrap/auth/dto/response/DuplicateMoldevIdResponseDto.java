package org.bootstrap.auth.dto.response;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record DuplicateMoldevIdResponseDto(
    Boolean isDuplicate
) {
    public static DuplicateMoldevIdResponseDto of(Boolean isDuplicate) {
        return DuplicateMoldevIdResponseDto.builder()
                .isDuplicate(isDuplicate)
                .build();
    }
}
