package org.bootstrap.auth.common.error;


public class UnAuthenticationException extends BaseErrorException {

    public UnAuthenticationException(GlobalErrorCode errorCode) {
        super(errorCode);
    }
}
