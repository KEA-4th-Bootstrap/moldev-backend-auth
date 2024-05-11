package org.bootstrap.auth.common.error;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Builder
public class ErrorWithDetailResponse<T> {

    private final String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    private final boolean success = false;

    private String code;
    private String status;
    private String reason;
    private T detail;

    public static <T> ErrorWithDetailResponse<?> from(ErrorReason<?> errorReason) {
        System.out.println(errorReason.getDetail());
        return ErrorWithDetailResponse.builder()
                .code(errorReason.getCode())
                .status(errorReason.getStatus().toString())
                .reason(errorReason.getReason())
                .detail(errorReason.getDetail())
                .build();
    }
}