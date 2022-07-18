package com.nejlasahin.booklistingproject.controller.endpoint;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AuthenticationEndpoint {

    public static final String BASE_PATH = "/api/v1/auth";
    public static final String LOGIN = "/login";
    public static final String SIGNUP = "/signup";
}
