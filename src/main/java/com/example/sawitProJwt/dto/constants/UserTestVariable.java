package com.example.sawitProJwt.dto.constants;

import com.example.sawitProJwt.dto.header.BaseHeader;
import com.example.sawitProJwt.dto.request.UserLoginRequest;
import com.example.sawitProJwt.dto.request.UserNameUpdateRequest;
import com.example.sawitProJwt.dto.request.UserRegistrationRequest;
import com.example.sawitProJwt.dto.response.BaseResponse;
import com.example.sawitProJwt.dto.response.UserNameOnlyResponse;
import com.example.sawitProJwt.dto.response.UserResponse;
import com.example.sawitProJwt.entities.User;
import com.example.sawitProJwt.exception.TokenInvalidException;
import com.example.sawitProJwt.utils.JwtUtils;
import com.example.sawitProJwt.utils.PasswordUtils;
import org.springframework.http.ResponseEntity;

import java.time.Duration;
import java.util.Date;

public class UserTestVariable {
    private static final Date currentDate = new Date();

    private static final Date updateDate = Date.from(currentDate.toInstant().plus(Duration.ofHours(1)));

    public static final User USER_DATA = User.builder()
            .id(1L)
            .name("Test 1")
            .phoneNumber("081111111111")
            .password(PasswordUtils.hashPassword("Test 1"))
            .createdDate(currentDate)
            .updatedDate(updateDate)
            .build();

    public static final UserRegistrationRequest USER_REGISTRATION_REQUEST = UserRegistrationRequest.builder()
            .name("Test 1")
            .phoneNumber("081111111111")
            .password("Test 1")
            .build();

    public static final UserResponse USER_RESPONSE = UserResponse.builder()
            .id(1L)
            .name("Test 1")
            .phoneNumber("081111111111")
            .createdDate(currentDate)
            .updatedDate(updateDate)
            .build();

    public static final UserResponse USER_RESPONSE_UPDATED = UserResponse.builder()
            .id(1L)
            .name("Test 2")
            .phoneNumber("081111111111")
            .createdDate(currentDate)
            .updatedDate(updateDate)
            .build();

    public static final UserLoginRequest USER_LOGIN_REQUEST = UserLoginRequest.builder()
            .phoneNumber("081111111111")
            .password("Test 1")
            .build();

    public static final UserLoginRequest USER_LOGIN_REQUEST_WRONG_PASSWORD = UserLoginRequest.builder()
            .phoneNumber("081111111111")
            .password("Test 2")
            .build();

    public static final UserNameOnlyResponse USER_NAME_ONLY_RESPONSE = UserNameOnlyResponse.builder()
            .name("Test 1")
            .build();

    public static final UserNameUpdateRequest USER_NAME_UPDATE_REQUEST = UserNameUpdateRequest.builder()
            .name("Test 2")
            .build();

    public static final BaseHeader BASE_HEADER_VALID;

    static {
        try {
            BASE_HEADER_VALID = BaseHeader.builder()
                    .authorization("Bearer " + JwtUtils.generateToken("081111111111"))
                    .build();
        } catch (TokenInvalidException e) {
            throw new RuntimeException(e);
        }
    }

    public static final BaseHeader BASE_HEADER_INVALID = BaseHeader.builder()
            .authorization("Bearer xxx")
            .build();

    public static final ResponseEntity<BaseResponse> BASE_RESPONSE_USER_REGISTER = ResponseEntity.ok().body(
            BaseResponse.builder()
                    .data(USER_RESPONSE)
                    .build()
    );
}
