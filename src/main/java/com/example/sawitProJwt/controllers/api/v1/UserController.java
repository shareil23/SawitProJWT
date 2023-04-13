package com.example.sawitProJwt.controllers.api.v1;

import com.example.sawitProJwt.dto.constants.ApiPath;
import com.example.sawitProJwt.dto.header.BaseHeader;
import com.example.sawitProJwt.dto.request.UserLoginRequest;
import com.example.sawitProJwt.dto.request.UserNameUpdateRequest;
import com.example.sawitProJwt.dto.request.UserRegistrationRequest;
import com.example.sawitProJwt.dto.response.BaseResponse;
import com.example.sawitProJwt.exception.CredentialsInvalidException;
import com.example.sawitProJwt.exception.ResourceDuplicateException;
import com.example.sawitProJwt.exception.ResourceNotFoundException;
import com.example.sawitProJwt.exception.TokenInvalidException;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(ApiPath.BASE_PATH_V1_USER)
public interface UserController {
    @PostMapping
    ResponseEntity<BaseResponse> userRegister(
            @Valid @RequestBody UserRegistrationRequest request
            ) throws ResourceDuplicateException;

    @PostMapping(ApiPath.LOGIN)
    ResponseEntity<BaseResponse> userLogin(
            @Valid @RequestBody UserLoginRequest request
            ) throws CredentialsInvalidException, TokenInvalidException;

    @GetMapping
    ResponseEntity<BaseResponse> userGetName(
            @Valid @RequestHeader("Authorization") BaseHeader header
            ) throws ResourceNotFoundException, TokenInvalidException;

    @PutMapping
    ResponseEntity<BaseResponse> userUpdateName(
            @Valid @RequestHeader("Authorization") BaseHeader header,
            @Valid @RequestBody UserNameUpdateRequest request
            ) throws ResourceNotFoundException, TokenInvalidException;
}
