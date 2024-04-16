package org.bootstrap.auth.common.error;


public class FileNotFoundException extends BaseErrorException {

    public FileNotFoundException(GlobalErrorCode errorCode) {
        super(errorCode);
    }
}
