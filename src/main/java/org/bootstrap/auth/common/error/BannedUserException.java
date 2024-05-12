package org.bootstrap.auth.common.error;

import lombok.Getter;

@Getter
public class BannedUserException extends BaseErrorException {

    public BannedUserException(GlobalErrorCode errorCode) {
        super(errorCode);
    }
}
