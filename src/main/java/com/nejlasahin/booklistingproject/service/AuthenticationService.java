package com.nejlasahin.booklistingproject.service;

import com.nejlasahin.booklistingproject.dto.request.LoginRequest;
import com.nejlasahin.booklistingproject.dto.request.SignupRequest;
import com.nejlasahin.booklistingproject.dto.response.LoginResponse;
import com.nejlasahin.booklistingproject.dto.response.SignupResponse;

public interface AuthenticationService {

    LoginResponse login(LoginRequest loginRequest);

    SignupResponse signup(SignupRequest signUpRequest);
}
