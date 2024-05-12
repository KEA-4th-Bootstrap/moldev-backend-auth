package org.bootstrap.auth.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import org.bootstrap.auth.entity.Ban;

import java.time.LocalDateTime;

@Builder(access = AccessLevel.PRIVATE)
public record BannedUserDetailResponseDto(
    LocalDateTime unbanDate,
    String reason
) {
    public static BannedUserDetailResponseDto of(Ban ban) {
        return BannedUserDetailResponseDto.builder()
                .unbanDate(ban.getUnbanDate())
                .reason(ban.getReason().getDesc())
                .build();
    }
}
