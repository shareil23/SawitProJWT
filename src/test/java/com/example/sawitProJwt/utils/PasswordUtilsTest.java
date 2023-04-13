package com.example.sawitProJwt.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;

public class PasswordUtilsTest {
    private static List<Object[]> hashPasswordTestCase() {
        /*
        @Param Test case title String
        @Param Input data String
        @Param Expected data Boolean
         */

        return Arrays.asList(
                new Object[]{
                        "Test Case #1 output expected",
                        "Test123",
                        true
                },
                new Object[]{
                        "Test Case #2 output unexpected",
                        "Test111",
                        false
                }
        );
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("hashPasswordTestCase")
    public void testHashPassword(String title,
                                 String input,
                                 Boolean expectedResult) {
        String hashedPassword = PasswordUtils.hashPassword(input);
        boolean actualOutput = PasswordUtils.checkPassword(input, hashedPassword);

        if (expectedResult) {
            Assertions.assertEquals(expectedResult, actualOutput);
        } else {
            Assertions.assertNotEquals(expectedResult, actualOutput);
        }

    }
}
