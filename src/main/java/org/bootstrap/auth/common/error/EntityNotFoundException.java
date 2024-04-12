package org.bootstrap.auth.common.error;

public class EntityNotFoundException extends BaseErrorException {

    public EntityNotFoundException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
