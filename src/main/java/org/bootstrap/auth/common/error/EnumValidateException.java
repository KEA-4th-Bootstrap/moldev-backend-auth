package org.bootstrap.auth.common.error;

public class EnumValidateException extends BaseErrorException {
    public static final BaseErrorException EXCEPTION = new EnumValidateException();

    public EnumValidateException() {
        super(GlobalErrorCode.INVALID_ENUM_CODE);
    }

}