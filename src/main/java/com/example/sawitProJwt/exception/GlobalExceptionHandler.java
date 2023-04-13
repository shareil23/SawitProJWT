package com.example.sawitProJwt.exception;

import com.example.sawitProJwt.dto.response.BaseResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
        BaseResponse response = BaseResponse.builder()
                .message("Error: " + ex.getMessage() + " Request Description: " + request.getDescription(Boolean.FALSE))
                .data(null)
                .build();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundExceptionHandler(ResourceNotFoundException ex, WebRequest request) {
        BaseResponse response = BaseResponse.builder()
                .message("Error: " + ex.getMessage() + " Request Description: " + request.getDescription(Boolean.FALSE))
                .data(null)
                .build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TokenInvalidException.class)
    public ResponseEntity<?> tokenInvalidExceptionHandler(TokenInvalidException ex, WebRequest request) {
        BaseResponse response = BaseResponse.builder()
                .message("Error: " + ex.getMessage() + " Request Description: " + request.getDescription(Boolean.FALSE))
                .data(null)
                .build();

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ResourceDuplicateException.class)
    public ResponseEntity<?> resourceDuplicateExceptionHandler(ResourceDuplicateException ex, WebRequest request) {
        BaseResponse response = BaseResponse.builder()
                .message("Error: " + ex.getMessage() + " Request Description: " + request.getDescription(Boolean.FALSE))
                .data(null)
                .build();

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CredentialsInvalidException.class)
    public ResponseEntity<?> credentialsInvalidExceptionHandler(CredentialsInvalidException ex, WebRequest request) {
        BaseResponse response = BaseResponse.builder()
                .message("Error: " + ex.getMessage() + " Request Description: " + request.getDescription(Boolean.FALSE))
                .data(null)
                .build();

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<?> expiredJwtExceptionHandler(ExpiredJwtException ex, WebRequest request) {
        BaseResponse response = BaseResponse.builder()
                .message("Error: " + ex.getMessage() + " Request Description: " + request.getDescription(Boolean.FALSE))
                .data(null)
                .build();

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<?> signatureExceptionHandler(SignatureException ex, WebRequest request) {
        BaseResponse response = BaseResponse.builder()
                .message("Error: " + ex.getMessage() + " Request Description: " + request.getDescription(Boolean.FALSE))
                .data(null)
                .build();

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex, WebRequest request) {
        List<String> error = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage).toList();

        BaseResponse response = BaseResponse.builder()
                .message("Error: " + error + " Request Description: " + request.getDescription(Boolean.FALSE))
                .data(null)
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
