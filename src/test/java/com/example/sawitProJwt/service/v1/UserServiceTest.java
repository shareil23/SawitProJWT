package com.example.sawitProJwt.service.v1;

import com.example.sawitProJwt.dto.constants.UserTestVariable;
import com.example.sawitProJwt.dto.request.UserLoginRequest;
import com.example.sawitProJwt.dto.request.UserNameUpdateRequest;
import com.example.sawitProJwt.dto.request.UserRegistrationRequest;
import com.example.sawitProJwt.dto.response.UserNameOnlyResponse;
import com.example.sawitProJwt.dto.response.UserResponse;
import com.example.sawitProJwt.entities.User;
import com.example.sawitProJwt.exception.CredentialsInvalidException;
import com.example.sawitProJwt.exception.ResourceDuplicateException;
import com.example.sawitProJwt.exception.ResourceNotFoundException;
import com.example.sawitProJwt.repositories.database.UserRepository;
import com.example.sawitProJwt.services.v1.UserServiceImpl;
import com.example.sawitProJwt.utils.PasswordUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private static List<Object[]> userRegistrationTestCase() {
        /*
        @Param Test case title String
        @Param Request for method input UserRegistrationRequest
        @Param Mock data User
        @Param Mock return from existsByPhoneNumber Boolean
        @Param Expected data UserResponse
        @Param Is Exception trigger Bool
        @Param Expected Exception Class Class
         */

        return Arrays.asList(
                new Object[]{
                        "Test Case #1 data created",
                        UserTestVariable.USER_REGISTRATION_REQUEST,
                        UserTestVariable.USER_DATA,
                        false,
                        UserTestVariable.USER_RESPONSE_UPDATED,
                        false,
                        Exception.class
                },
                new Object[]{
                        "Test Case #2 data exists",
                        UserTestVariable.USER_REGISTRATION_REQUEST,
                        UserTestVariable.USER_DATA,
                        true,
                        UserTestVariable.USER_RESPONSE,
                        true,
                        ResourceDuplicateException.class
                }
        );
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("userRegistrationTestCase")
    public void testUserRegistration(String title,
                                     UserRegistrationRequest request,
                                     User mockingData,
                                     Boolean mockingStatus,
                                     UserResponse expectedData,
                                     Boolean isException,
                                     Class exceptionClass) throws Exception {
        when(userRepository.existsByPhoneNumber(request.getPhoneNumber())).thenReturn(mockingStatus);

        if (isException) {
            Assertions.assertThrows(
                    exceptionClass,
                    () -> {
                        userService.userRegistration(request);
                    }
            );
        } else {
            lenient().when(
                    userRepository.save(
                            any(User.class)
                    )
            ).thenAnswer(invocation -> mockingData);

            UserResponse actualData = userService.userRegistration(request);

            Assertions.assertEquals(expectedData.getName(), actualData.getName());
            Assertions.assertTrue(PasswordUtils.checkPassword(request.getPassword(), mockingData.getPassword()));
        }
    }

    private static List<Object[]> userLoginTestCase() {
        /*
        @Param Test case title String
        @Param Request for method input UserLoginRequest
        @Param Mock data User
        @Param Expected data User
        @Param Is Exception trigger Bool
        @Param Expected Exception Class Class
         */

        return Arrays.asList(
                new Object[]{
                        "Test Case #1 success logged",
                        UserTestVariable.USER_LOGIN_REQUEST,
                        UserTestVariable.USER_DATA,
                        UserTestVariable.USER_DATA,
                        false,
                        Exception.class
                },
                new Object[]{
                        "Test Case #2 data not exists",
                        UserTestVariable.USER_LOGIN_REQUEST,
                        null,
                        null,
                        true,
                        CredentialsInvalidException.class
                },
                new Object[]{
                        "Test Case #3 data wrong password",
                        UserTestVariable.USER_LOGIN_REQUEST_WRONG_PASSWORD,
                        null,
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
                              User expectedData,
                              Boolean isException,
                              Class exceptionClass) throws Exception {
        when(userRepository.findByPhoneNumber(request.getPhoneNumber())).thenReturn(Optional.ofNullable(mockingData));

        if (isException) {
            Assertions.assertThrows(
                    exceptionClass,
                    () -> {
                        userService.userLogin(request);
                    }
            );
        } else {
            User actualData = userService.userLogin(request);

            Assertions.assertEquals(expectedData, actualData);
        }
    }

    private static List<Object[]> userGetNameTestCase() {
        /*
        @Param Test case title String
        @Param Request for method input String
        @Param Mock data User
        @Param Expected data UserNameOnlyResponse
        @Param Is Exception trigger Bool
        @Param Expected Exception Class Class
         */

        return Arrays.asList(
                new Object[]{
                        "Test Case #1 get user name",
                        "081111111111",
                        UserTestVariable.USER_DATA,
                        UserTestVariable.USER_NAME_ONLY_RESPONSE,
                        false,
                        Exception.class
                },
                new Object[]{
                        "Test Case #2 data not exists",
                        "081111111112",
                        null,
                        null,
                        true,
                        ResourceNotFoundException.class
                }
        );
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("userGetNameTestCase")
    public void testUserGetName(String title,
                              String phoneNumber,
                              User mockingData,
                              UserNameOnlyResponse expectedData,
                              Boolean isException,
                              Class exceptionClass) throws Exception {
        when(userRepository.findByPhoneNumber(phoneNumber)).thenReturn(Optional.ofNullable(mockingData));

        if (isException) {
            Assertions.assertThrows(
                    exceptionClass,
                    () -> {
                        userService.userGetName(phoneNumber);
                    }
            );
        } else {
            UserNameOnlyResponse actualData = userService.userGetName(phoneNumber);

            Assertions.assertEquals(expectedData, actualData);
        }
    }

    private static List<Object[]> userUpdateNameTestCase() {
        /*
        @Param Test case title String
        @Param Request for method input UserNameUpdateRequest
        @Param 2nd request for method input phone number String
        @Param Mock data User
        @Param Expected data UserResponse
        @Param Is Exception trigger Bool
        @Param Expected Exception Class Class
         */

        return Arrays.asList(
                new Object[]{
                        "Test Case #1 update user name",
                        UserTestVariable.USER_NAME_UPDATE_REQUEST,
                        "081111111111",
                        UserTestVariable.USER_DATA,
                        UserTestVariable.USER_RESPONSE_UPDATED,
                        false,
                        Exception.class
                },
                new Object[]{
                        "Test Case #2 data not exists",
                        UserTestVariable.USER_NAME_UPDATE_REQUEST,
                        "081111111112",
                        null,
                        null,
                        true,
                        ResourceNotFoundException.class
                }
        );
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("userUpdateNameTestCase")
    public void testUserUpdateName(String title,
                                UserNameUpdateRequest request,
                                String phoneNumber,
                                User mockingData,
                                UserResponse expectedData,
                                Boolean isException,
                                Class exceptionClass) throws Exception {
        when(userRepository.findByPhoneNumber(phoneNumber)).thenReturn(Optional.ofNullable(mockingData));

        if (isException) {
            Assertions.assertThrows(
                    exceptionClass,
                    () -> {
                        userService.userUpdateName(request, phoneNumber);
                    }
            );
        } else {
            UserResponse actualData = userService.userUpdateName(request, phoneNumber);

            Assertions.assertEquals(expectedData.getName(), actualData.getName());
        }
    }
}
