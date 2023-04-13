package com.example.sawitProJwt.utils;

import com.example.sawitProJwt.dto.constants.PropertiesData;
import com.example.sawitProJwt.exception.TokenInvalidException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Date;

public class JwtUtils {
    private static PrivateKey getPrivateKey(String privateFile) throws TokenInvalidException {
        try {
            String privateKeyContent = new String(Files.readAllBytes(Paths.get(privateFile)));
            privateKeyContent = privateKeyContent.replace("-----BEGIN PRIVATE KEY-----\n", "")
                    .replace("\n-----END PRIVATE KEY-----\n", "")
                    .replace("\n", "");
            byte[] privateKeyBytes = java.util.Base64.getDecoder().decode(privateKeyContent);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

            return privateKey;
        } catch (Exception e) {
            System.out.println("Full Path: " + Arrays.toString(Thread.currentThread().getStackTrace()) + " Error: " + e);
            throw new TokenInvalidException("Token is invalid");
        }
    }

    private static PublicKey getPublicKey(String publicFile) throws TokenInvalidException {
        try {
            String publicKeyContent = new String(Files.readAllBytes(Paths.get(PropertiesData.PUBLIC_FILE)));
            publicKeyContent = publicKeyContent.replace("-----BEGIN PUBLIC KEY-----\n", "")
                    .replace("\n-----END PUBLIC KEY-----\n", "")
                    .replace("\n", "");
            byte[] publicKeyBytes = java.util.Base64.getDecoder().decode(publicKeyContent);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(keySpec);

            return publicKey;
        } catch (Exception e) {
            System.out.println("Full Path: " + Arrays.toString(Thread.currentThread().getStackTrace()) + " Error: " + e);
            throw new TokenInvalidException("Token is invalid");
        }
    }

    public static String generateToken(String phoneNumber) throws TokenInvalidException {
        Date currentTime = new Date();
        Date expiration = new Date(currentTime.getTime() + PropertiesData.EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(phoneNumber)
                .setIssuedAt(currentTime)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.RS256, getPrivateKey(PropertiesData.PRIVATE_FILE))
                .compact();
    }

    public static String getPhoneNumberFromToken(String token) throws TokenInvalidException {
        String formattedToken = token.substring(7);

        return Jwts.parserBuilder()
                .setSigningKey(getPublicKey(PropertiesData.PUBLIC_FILE))
                .build()
                .parseClaimsJws(formattedToken)
                .getBody()
                .getSubject();
    }

    public static Boolean validateToken(String token) throws TokenInvalidException {
        String formattedToken = token.substring(7);

        try {
            Jwts.parserBuilder()
                    .setSigningKey(getPublicKey(PropertiesData.PUBLIC_FILE))
                    .build()
                    .parseClaimsJws(formattedToken);

            return true;
        } catch (Exception e) {
            throw new TokenInvalidException("Token is invalid");
        }
    }
}
