package com.example.sawitProJwt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ResourceDuplicateException extends Exception {
    private static final Long serialVersionUID = 1L;

    public ResourceDuplicateException(String message) {
        super(message);
    }
}
