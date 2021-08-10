package dev.calculator.config.error;

import org.springframework.security.core.AuthenticationException;

public class RestAuthenticationException extends AuthenticationException {
    public RestAuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public RestAuthenticationException(String msg) {
        super(msg);
    }
}
