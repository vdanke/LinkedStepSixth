package org.step.linked.step.exception;

import org.springframework.security.core.AuthenticationException;

public class AuthenticationProviderException extends AuthenticationException {

    public AuthenticationProviderException(String message) {
        super(message);
    }

    public AuthenticationProviderException(String message, Throwable cause) {
        super(message, cause);
    }
}
