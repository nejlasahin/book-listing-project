package com.nejlasahin.booklistingproject.controller.unit;

import com.nejlasahin.booklistingproject.controller.AuthenticationController;
import com.nejlasahin.booklistingproject.dto.base.RestResponse;
import com.nejlasahin.booklistingproject.dto.request.LoginRequest;
import com.nejlasahin.booklistingproject.dto.request.SignupRequest;
import com.nejlasahin.booklistingproject.dto.response.LoginResponse;
import com.nejlasahin.booklistingproject.dto.response.SignupResponse;
import com.nejlasahin.booklistingproject.model.enumeration.RoleNameEnum;
import com.nejlasahin.booklistingproject.service.AuthenticationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationControllerUnitTest {

    @InjectMocks
    AuthenticationController authenticationController;

    @Mock
    AuthenticationService authenticationService;

    LoginRequest loginRequest;
    LoginResponse loginResponse;
    SignupRequest signupRequest;
    SignupResponse signupResponse;

    @Before
    public void setup() {
        loginRequest = buildLoginRequest("username1", "password1");
        loginResponse = buildLoginResponse("token.1", "1", "username1", "username1@gmail.com", RoleNameEnum.ROLE_AUTHOR);
        signupRequest = buildSignupRequest("username1", "firstName1", "lastName1", "username1@gmail.com", "password1", RoleNameEnum.ROLE_AUTHOR);
        signupResponse = buildSignupResponse("User registered successfully!");
    }

    @Test
    public void shouldReturnLoginResponse_WhenLogin() {

        when(authenticationService.login(any())).thenReturn(loginResponse);

        ResponseEntity<RestResponse<LoginResponse>> response = authenticationController.login(loginRequest);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().getData().getUsername(), loginRequest.getUsername());
    }

    @Test
    public void shouldReturnSignupResponse_WhenSignup() {

        when(authenticationService.signup(any())).thenReturn(signupResponse);

        ResponseEntity<RestResponse<SignupResponse>> response = authenticationController.signup(signupRequest);

        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertTrue(response.getBody().getData().getMessage().length() > 0);
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
