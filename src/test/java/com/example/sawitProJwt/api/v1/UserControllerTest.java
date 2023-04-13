package com.example.sawitProJwt.api.v1;

import com.example.sawitProJwt.controllers.api.v1.UserControllerImpl;
import com.example.sawitProJwt.dto.constants.UserTestVariable;
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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserControllerImpl userController;

    public static List<Object[]> userRegisterTestCase() {
        /*
        @Param Test case title String
        @Param Body request data UserRegistrationRequest
        @Param Mocked data UserResponse
        @Param Expected data ResponseEntity<BaseResponse>
        @Param Is Expectation trigger Bool
        @Param Expected Expectation Class
         */

        return Arrays.asList(
                new Object[] {
                        "Test Case #1 user registered",
                        UserTestVariable.USER_REGISTRATION_REQUEST,
                        UserTestVariable.USER_RESPONSE,
                        UserTestVariable.BASE_RESPONSE_USER_REGISTER,
                        false,
                        Exception.class
                },
                new Object[] {
                        "Test Case #2 user register throw ResourceDuplicateException",
                        UserTestVariable.USER_REGISTRATION_REQUEST,
                        null,
                        null,
                        true,
                        ResourceDuplicateException.class
                }
        );
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("userRegisterTestCase")
    public void testUserRegister(String title,
                                 UserRegistrationRequest request,
                                 UserResponse mockingData,
                                 ResponseEntity<BaseResponse> expectedResponse,
                                 Boolean isExpected,
                                 Class exceptionClass) throws Exception {
        if (isExpected) {
            when(userService.userRegistration(request)).thenThrow(exceptionClass);

            Assertions.assertThrows(
                    exceptionClass,
                    () -> {
                        userController.userRegister(request);
                    }
            );
        } else {
            when(userService.userRegistration(request)).thenReturn(mockingData);

            ResponseEntity<BaseResponse> actualResponse = userController.userRegister(request);
            UserResponse actualUserResponse = (UserResponse) actualResponse.getBody().getData();
            UserResponse expectedUserResponse = (UserResponse) expectedResponse.getBody().getData();

            Assertions.assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
            Assertions.assertEquals(expectedUserResponse.getName(), actualUserResponse.getName());
            Assertions.assertEquals(expectedUserResponse.getPhoneNumber(), actualUserResponse.getPhoneNumber());
        }
    }

    public static List<Object[]> userLoginTestCase() {
        /*
        @Param Test case title String
        @Param Body request data UserLoginRequest
        @Param Mocked data User
        @Param Is Expectation trigger Bool
        @Param Expected Expectation Class
         */

        return Arrays.asList(
                new Object[] {
                        "Test Case #1 user login success",
                        UserTestVariable.USER_LOGIN_REQUEST,
                        UserTestVariable.USER_DATA,
                        false,
                        Exception.class
                },
                new Object[] {
                        "Test Case #2 user login failed",
                        UserTestVariable.USER_LOGIN_REQUEST,
                        null,
                        true,
                        CredentialsInvalidException.class
                }
        );
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("userLoginTestCase")
    public void testUserLogin(String title,
                              UserLoginRequest request,
                              User mockingData,
                              Boolean isExpected,
                              Class exceptionClass) throws Exception {
        if (isExpected) {
            when(userService.userLogin(request)).thenThrow(exceptionClass);

            Assertions.assertThrows(
                    exceptionClass,
                    () -> {
                        userController.userLogin(request);
                    }
            );
        } else {
            when(userService.userLogin(request)).thenReturn(mockingData);

            ResponseEntity<BaseResponse> actualResponse = userController.userLogin(request);
            TokenOnlyResponse actualUserResponse = (TokenOnlyResponse) actualResponse.getBody().getData();

            Assertions.assertTrue(JwtUtils.validateToken("Bearer " + actualUserResponse.getToken()));
            Assertions.assertEquals(request.getPhoneNumber(),
                    JwtUtils.getPhoneNumberFromToken("Bearer " + actualUserResponse.getToken()));
        }
    }

    public static List<Object[]> userGetNameTestCase() {
        /*
        @Param Test case title String
        @Param Header request data BaseHeader
        @Param Mocked data User
        @Param Mocked 2nd data UserNameOnlyResponse
        @Param Is Expectation trigger Bool
        @Param Expected Expectation Class
         */

        return Arrays.asList(
                new Object[] {
                        "Test Case #1 user get name success",
                        UserTestVariable.BASE_HEADER_VALID,
                        UserTestVariable.USER_DATA,
                        UserTestVariable.USER_NAME_ONLY_RESPONSE,
                        false,
                        Exception.class
                },
                new Object[] {
                        "Test Case #2 user get name not found",
                        UserTestVariable.BASE_HEADER_VALID,
                        UserTestVariable.USER_DATA,
                        null,
                        true,
                        ResourceNotFoundException.class
                }
        );
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("userGetNameTestCase")
    public void testUserGetName(String title,
                              BaseHeader request,
                              User mockingData,
                              UserNameOnlyResponse mockingData2,
                              Boolean isExpected,
                              Class exceptionClass) throws Exception {
        if (isExpected) {
            when(userService.userGetName(mockingData.getPhoneNumber())).thenThrow(exceptionClass);

            Assertions.assertThrows(
                    exceptionClass,
                    () -> {
                        userService.userGetName(mockingData.getPhoneNumber());
                    }
            );
        } else {
            when(userService.userGetName(mockingData.getPhoneNumber())).thenReturn(mockingData2);

            ResponseEntity<BaseResponse> actualResponse = userController.userGetName(request);
            UserNameOnlyResponse actualUserResponse = (UserNameOnlyResponse) actualResponse.getBody().getData();

            Assertions.assertNotNull(actualUserResponse.getName());

        }
    }

    public static List<Object[]> userUpdateNameTestCase() {
        /*
        @Param Test case title String
        @Param Header request data BaseHeader
        @Param Body request data UserNameUpdateRequest
        @Param Mocked data UserResponse
        @Param Is Expectation trigger Bool
        @Param Expected Expectation Class
         */

        return Arrays.asList(
                new Object[] {
                        "Test Case #1 user get name success",
                        UserTestVariable.BASE_HEADER_VALID,
                        UserTestVariable.USER_NAME_UPDATE_REQUEST,
                        UserTestVariable.USER_RESPONSE_UPDATED,
                        false,
                        Exception.class
                },
                new Object[] {
                        "Test Case #2 user get name not found",
                        UserTestVariable.BASE_HEADER_VALID,
                        UserTestVariable.USER_NAME_UPDATE_REQUEST,
                        UserTestVariable.USER_RESPONSE_UPDATED,
                        true,
                        ResourceNotFoundException.class
                }
        );
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("userUpdateNameTestCase")
    public void testUserUpdateName(String title,
                                BaseHeader header,
                                UserNameUpdateRequest request,
                                UserResponse mockingData,
                                Boolean isExpected,
                                Class exceptionClass) throws Exception {
        if (isExpected) {
            when(userService.userUpdateName(request, mockingData.getPhoneNumber())).thenThrow(exceptionClass);

            Assertions.assertThrows(
                    exceptionClass,
                    () -> {
                        userService.userUpdateName(request, mockingData.getPhoneNumber());
                    }
            );
        } else {
            when(userService.userUpdateName(request, mockingData.getPhoneNumber())).thenReturn(mockingData);

            ResponseEntity<BaseResponse> actualResponse = userController.userUpdateName(header, request);
            UserResponse actualUserResponse = (UserResponse) actualResponse.getBody().getData();

            Assertions.assertEquals(request.getName(), actualUserResponse.getName());

        }
    }
}
