package com.example.sawitProJwt.utils;

import com.example.sawitProJwt.exception.TokenInvalidException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;

public class JwtUtilsTest {
    private static List<Object[]> jwtTestCase() {
        /*
        @Param Test case title String
        @Param Input data String
        @Param Expected data Boolean
        @Param is Exception Boolean
        @Param Exception class to trigger Class
         */

        return Arrays.asList(
                new Object[]{
                        "Test Case #1 output expected",
                        "081111111111",
                        true,
                        false,
                        Exception.class
                },
                new Object[]{
                        "Test Case #2 output unexpected",
                        "081111111112",
                        false,
                        true,
                        TokenInvalidException.class
                }
        );
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("jwtTestCase")
    public void testJwt(String title,
                        String phoneNumber,
                        Boolean expectedResult,
                        Boolean isException,
                        Class exceptionClass) throws Exception {
        String token = JwtUtils.generateToken(phoneNumber);
        String formattedToken = "Bearer " + token;

        if (isException) {
            Assertions.assertThrows(
                    exceptionClass,
                    () -> {
                        JwtUtils.validateToken(formattedToken.substring(7));
                    }
            );
        } else {
            Boolean actualDataValidation = JwtUtils.validateToken(formattedToken);
            Assertions.assertEquals(expectedResult, actualDataValidation);

            String actualDataPhoneNumber = JwtUtils.getPhoneNumberFromToken(formattedToken);
            Assertions.assertEquals(actualDataPhoneNumber, phoneNumber);
        }


    }
}
