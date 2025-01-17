package org.bootstrap.auth.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum GlobalErrorCode implements BaseErrorCode {

    /* Authentication error */
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "GLOBAL_401_1", "인증되지 않은 사용자입니다."),
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "GLOBAL_401_2", "유효하지 않은 Access Token 입니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "GLOBAL_401_3", "유효하지 않은 Refresh Token 입니다."),
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "GLOBAL_401_4", "만료된 Access Token 입니다."),
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "GLOBAL_401_5", "만료된 Refresh Token 입니다."),
    WRONG_PASSWORD(HttpStatus.UNAUTHORIZED, "GLOBAL_401_6", "비밀번호가 일치하지 않습니다."),
    INVALID_EMAIL_CODE(HttpStatus.UNAUTHORIZED, "GLOBAL_401_7", "유효하지 않은 이메일 코드입니다."),
    BANNED_USER(HttpStatus.UNAUTHORIZED, "GLOBAL_401_8", "차단된 사용자입니다."),

    /* global error */
    HTTP_MESSAGE_NOT_READABLE(BAD_REQUEST,"GLOBAL_400_1", "잘못된 형식의 값을 입력했습니다."),
    _INTERNAL_SERVER_ERROR(INTERNAL_SERVER_ERROR, "GLOBAL_500_1", "서버 오류. 관리자에게 문의 부탁드립니다."),

    /*Enum error*/
    INVALID_ENUM_CODE(BAD_REQUEST, "ENUM_400_1", "잘못된 Enum class code 입니다."),

    ENTITY_NOT_FOUND(NOT_FOUND, "GLOBAL_404_1", "해당 데이터를 찾을 수 없습니다."),
    USER_NOT_FOUND(NOT_FOUND, "GLOBAL_404_2", "해당 사용자를 찾을 수 없습니다."),
    FILE_NOT_FOUND(NOT_FOUND, "GLOBAL_404_3", "해당 파일을 찾을 수 없습니다."),

    DUPLICATE_EMAIL(CONFLICT, "GLOBAL_409_1", "이미 존재하는 이메일입니다."),
    DUPLICATE_MOLDEVID(CONFLICT, "GLOBAL_409_2", "이미 존재하는 몰데브 아이디입니다."),
    FILE_UPLOAD_ERROR(CONFLICT, "GLOBAL_409_3", "파일 업로드 중 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String reason;
    @Setter
    private Object detail = null;

    @Override
    public ErrorReason getErrorReason() {
        if (detail != null)
            return ErrorReason.of(status.value(), code, reason, detail);
        return ErrorReason.of(status.value(), code, reason);
    }
}
