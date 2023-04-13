package com.example.sawitProJwt.dto.constants;

public interface ApiPath {
    String ROOT = "/api";
    String V1 = "/v1";
    String USER = "/user";

    String BASE_PATH_V1 =  ROOT + V1;

    String BASE_PATH_V1_USER = BASE_PATH_V1 + USER;
    String LOGIN = "/login";
}
