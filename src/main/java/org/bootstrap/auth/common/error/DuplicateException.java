package org.bootstrap.auth.common.error;


public class DuplicateException extends BaseErrorException {

    public DuplicateException(GlobalErrorCode errorCode) {
        super(errorCode);
    }
}
