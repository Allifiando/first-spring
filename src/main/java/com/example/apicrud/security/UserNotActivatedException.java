package com.example.apicrud.security;

import org.springframework.security.core.AuthenticationException;

public class UserNotActivatedException extends AuthenticationException {

    public UserNotActivatedException(String message) {
        super(message);
    }
}
