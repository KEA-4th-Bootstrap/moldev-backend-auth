package org.bootstrap.auth.redis.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Builder
@RedisHash(value = "emailVerificationCode", timeToLive = 60 * 5)
public class EmailVerificationCode {

    @Id
    private String email;

    private String code;

    public static EmailVerificationCode of(String email, String code) {
        return EmailVerificationCode.builder()
                .email(email)
                .code(code)
                .build();
    }
}
