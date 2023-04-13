package com.example.sawitProJwt.dto.constants;

import org.springframework.beans.factory.annotation.Value;

public class PropertiesData {
    @Value("${jwt.private-file}")
    public static final String PRIVATE_FILE = "private.pem";

    @Value("${jwt.public-file}")
    public static final String PUBLIC_FILE = "public.pem";

    @Value("${jwt.expiration-time}")
    public static final Long EXPIRATION_TIME = 864000000L;
}
