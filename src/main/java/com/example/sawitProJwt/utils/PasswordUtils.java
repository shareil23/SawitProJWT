package com.example.sawitProJwt.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordUtils {
    private static final int SALT_ROUNDS = 10;

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(SALT_ROUNDS));
    }

    public static Boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
