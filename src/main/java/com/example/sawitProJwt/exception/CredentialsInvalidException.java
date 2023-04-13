package com.example.sawitProJwt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class CredentialsInvalidException extends Exception {
    private static final Long serialVersionUID = 1L;

    public CredentialsInvalidException(String message) {
        super(message);
    }
}
