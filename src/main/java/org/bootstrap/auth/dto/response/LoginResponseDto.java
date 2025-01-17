package org.bootstrap.auth.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import org.bootstrap.auth.entity.Member;
import org.bootstrap.auth.jwt.Token;

@Builder(access = AccessLevel.PRIVATE)
public record LoginResponseDto (
    String moldevId,
    String nickname,
    String accessToken
) {
    public static LoginResponseDto of(Member member, Token token) {
        return LoginResponseDto.builder()
                .moldevId(member.getMoldevId())
                .nickname(member.getNickname())
                .accessToken(token.getAccessToken())
                .build();
    }
}
