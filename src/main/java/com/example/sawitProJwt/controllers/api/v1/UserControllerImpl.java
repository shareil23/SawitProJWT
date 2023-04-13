package com.example.sawitProJwt.controllers.api.v1;

import com.example.sawitProJwt.dto.constants.ApiPath;
import com.example.sawitProJwt.dto.header.BaseHeader;
import com.example.sawitProJwt.dto.request.UserLoginRequest;
import com.example.sawitProJwt.dto.request.UserNameUpdateRequest;
import com.example.sawitProJwt.dto.request.UserRegistrationRequest;
import com.example.sawitProJwt.dto.response.BaseResponse;
import com.example.sawitProJwt.dto.response.TokenOnlyResponse;
import com.example.sawitProJwt.dto.response.UserNameOnlyResponse;
import com.example.sawitProJwt.dto.response.UserResponse;
import com.example.sawitProJwt.entities.User;
import com.example.sawitProJwt.exception.CredentialsInvalidException;
import com.example.sawitProJwt.exception.ResourceDuplicateException;
import com.example.sawitProJwt.exception.ResourceNotFoundException;
import com.example.sawitProJwt.exception.TokenInvalidException;
import com.example.sawitProJwt.services.v1.UserService;
import com.example.sawitProJwt.utils.JwtUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User Controller")
@RestController
@RequestMapping(ApiPath.BASE_PATH_V1_USER)
public class UserControllerImpl implements UserController{
    private final UserService userService;

    @Autowired
    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<BaseResponse> userRegister(
            UserRegistrationRequest request) throws ResourceDuplicateException {
        UserResponse userResponse = userService.userRegistration(request);
        BaseResponse response = BaseResponse.builder()
                .data(userResponse)
                .build();

        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<BaseResponse> userLogin(
            UserLoginRequest request) throws CredentialsInvalidException, TokenInvalidException {
        User user = userService.userLogin(request);
        String token = JwtUtils.generateToken(user.getPhoneNumber());
        BaseResponse response = BaseResponse.builder()
                .data(TokenOnlyResponse.builder().token(token).build())
                .build();

        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<BaseResponse> userGetName(
            BaseHeader header) throws ResourceNotFoundException, TokenInvalidException {
        System.out.println("Token: " + header.getAuthorization());
        JwtUtils.validateToken(header.getAuthorization());

        String phoneNumber = JwtUtils.getPhoneNumberFromToken(header.getAuthorization());
        UserNameOnlyResponse userNameOnlyResponse = userService.userGetName(phoneNumber);
        BaseResponse response = BaseResponse.builder()
                .data(userNameOnlyResponse)
                .build();

        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<BaseResponse> userUpdateName(
            BaseHeader header,
            UserNameUpdateRequest request) throws ResourceNotFoundException, TokenInvalidException {
        JwtUtils.validateToken(header.getAuthorization());

        String phoneNumber = JwtUtils.getPhoneNumberFromToken(header.getAuthorization());
        UserResponse userResponse = userService.userUpdateName(request, phoneNumber);
        BaseResponse response = BaseResponse.builder()
                .data(userResponse)
                .build();

        return ResponseEntity.ok().body(response);
    }
}
