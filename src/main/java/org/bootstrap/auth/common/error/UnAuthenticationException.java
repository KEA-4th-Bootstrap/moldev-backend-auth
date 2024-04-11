package org.bootstrap.auth.common.error;


public class UnAuthenticationException extends RuntimeException{

    public UnAuthenticationException(String message) {
        super(message);
    }
}
