package com.nejlasahin.booklistingproject.service.integration;

import com.nejlasahin.booklistingproject.dto.request.LoginRequest;
import com.nejlasahin.booklistingproject.dto.request.SignupRequest;
import com.nejlasahin.booklistingproject.dto.response.LoginResponse;
import com.nejlasahin.booklistingproject.dto.response.SignupResponse;
import com.nejlasahin.booklistingproject.model.enumeration.RoleNameEnum;
import com.nejlasahin.booklistingproject.service.impl.AuthenticationServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AuthenticationServiceIntegrationTest {

    @Autowired
    AuthenticationServiceImpl authenticationService;

    LoginRequest loginRequest;
    LoginResponse loginResponse;
    SignupRequest signupRequest;
    SignupResponse signupResponse;

    @Before
    public void setup() {
        loginRequest = buildLoginRequest("author", "123456");
        loginResponse = buildLoginResponse("token.1", "1", "username1", "username1@gmail.com", RoleNameEnum.ROLE_AUTHOR);
        signupRequest = buildSignupRequest("username1", "firstName1", "lastName1", "username1@gmail.com", "password1", RoleNameEnum.ROLE_AUTHOR);
        signupResponse = buildSignupResponse("User registered successfully!");
    }

    @Test
    public void shouldReturnLoginResponse_WhenLogin() {

        LoginResponse response = authenticationService.login(loginRequest);

        assertEquals(response.getUsername(), loginRequest.getUsername());
    }

    @Test
    public void shouldReturnSignupResponse_WhenSignup() {

        SignupResponse response = authenticationService.signup(signupRequest);

        assertTrue(response.getMessage().length() > 0);
    }

    private LoginRequest buildLoginRequest(String username, String password) {
        return LoginRequest.builder()
                .username(username)
                .password(password)
                .build();
    }

    private LoginResponse buildLoginResponse(String token, String id, String username, String email, RoleNameEnum roleName) {
        return LoginResponse.builder()
                .token(token)
                .type("Bearer")
                .id(id)
                .username(username)
                .email(email)
                .roles(List.of(roleName.name()))
                .build();
    }


    private SignupRequest buildSignupRequest(String username, String firstName, String lastName, String email, String password, RoleNameEnum roleName) {
        return SignupRequest.builder()
                .username(username)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(password)
                .roleName(roleName.name())
                .build();
    }

    private SignupResponse buildSignupResponse(String message) {
        return SignupResponse.builder()
                .message(message)
                .build();
    }
}
