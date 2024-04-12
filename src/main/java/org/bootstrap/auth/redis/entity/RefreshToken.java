package org.bootstrap.auth.redis.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Builder
@RedisHash(value = "refreshToken")
public class RefreshToken {

    @Id
    private Long memberId;

    private String refreshToken;

    public static RefreshToken create(Long memberId, String refreshToken) {
        return RefreshToken.builder()
                .memberId(memberId)
                .refreshToken(refreshToken)
                .build();
    }

    public void update(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}
