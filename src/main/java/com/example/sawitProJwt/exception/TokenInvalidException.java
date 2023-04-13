package com.example.sawitProJwt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class TokenInvalidException extends Exception {
    private static final Long serialVersionUID = 1L;

    public TokenInvalidException(String message) {
        super(message);
    }
}
