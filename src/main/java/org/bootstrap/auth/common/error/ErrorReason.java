package org.bootstrap.auth.common.error;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorReason<T> {

    private final Integer status;
    private final String code;
    private final String reason;
    private final T detail;

    public static ErrorReason of(Integer status, String code, String reason) {
        return ErrorReason.builder()
                .status(status)
                .code(code)
                .reason(reason)
                .build();
    }

    public static <T> ErrorReason<?> of(Integer status, String code, String reason, T detail) {
        return ErrorReason.builder()
                .status(status)
                .code(code)
                .reason(reason)
                .detail(detail)
                .build();
    }
}