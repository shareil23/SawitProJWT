package com.example.sawitProJwt.services.v1;

import com.example.sawitProJwt.dto.request.UserLoginRequest;
import com.example.sawitProJwt.dto.request.UserNameUpdateRequest;
import com.example.sawitProJwt.dto.request.UserRegistrationRequest;
import com.example.sawitProJwt.dto.response.UserNameOnlyResponse;
import com.example.sawitProJwt.dto.response.UserResponse;
import com.example.sawitProJwt.entities.User;
import com.example.sawitProJwt.exception.CredentialsInvalidException;
import com.example.sawitProJwt.exception.ResourceDuplicateException;
import com.example.sawitProJwt.exception.ResourceNotFoundException;
import com.example.sawitProJwt.exception.TokenInvalidException;

public interface UserService {
    UserResponse userRegistration(UserRegistrationRequest request) throws ResourceDuplicateException;

    User userLogin(UserLoginRequest request) throws CredentialsInvalidException;

    UserNameOnlyResponse userGetName(String phoneNumber) throws ResourceNotFoundException;

    UserResponse userUpdateName(UserNameUpdateRequest request, String phoneNumber) throws ResourceNotFoundException;
}
