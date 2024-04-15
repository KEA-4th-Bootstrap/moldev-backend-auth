package org.bootstrap.auth.common.error;


public class FileUploadErrorException extends BaseErrorException {

    public FileUploadErrorException(GlobalErrorCode errorCode) {
        super(errorCode);
    }
}
